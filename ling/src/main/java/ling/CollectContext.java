package ling;

import java.util.*;

public class CollectContext {

    private Omograph omograph = new Omograph();
    private List<ContextInfo> ClNearR = new LinkedList(); //Список для второго слова после омографа
    private List<ContextInfo> ClNearL = new LinkedList(); //Список для второго слова перед омографом

    Omograph collect(Token[][] tokens)
    {
        if (tokens == null)
            throw new NullPointerException("tokens may not be null");
        ContextInfo NullContext = new ContextInfo(); //Нулевой контекст - заглушка
        NullContext.word = "";
        ContextInfo CiNear = new ContextInfo(); //Контекст для второго слова
        ContextInfo CiFar = new ContextInfo(); //Контекст для третьего слова
        int flag;
        //Убираем из предложения знаки пунктуации и пробелы
        for (Token[] sentence : tokens) {
            List<Token> Tl = new LinkedList();
            for (Token token : sentence) {
                if (token.type != Token.Type.SPACE && token.type != Token.Type.PUNC)
                    Tl.add(token);
            }
            Token[] sentwithout = Tl.toArray(new Token[Tl.size()]);
            //Проходим по предложению и ищем совпадение с омографом
            for (int j = 0; j < sentwithout.length; j++) {
                if (sentwithout[j].text.equals("Стоит") || sentwithout[j].text.equals("стоит")) {
                    if (j + 1 < sentwithout.length) //Если справа есть токен
                    {
                        if (j + 2 < sentwithout.length) //Если справа есть два токена
                        {
                            flag = 0;
                            //Ищем cуществующий контекст слова
                            for (ContextInfo contextInfo : ClNearR)
                                if (contextInfo.word.equals(sentwithout[j + 1].text)) {
                                    CiNear = contextInfo;
                                    flag = 1;
                                }
                            //Если его нет создаём новый и добавляем его в список контекстов
                            if (flag == 0) {
                                CiNear = new ContextInfo();
                                CiNear.word = sentwithout[j + 1].text;
                                ClNearR.add(CiNear);
                            }
                            if (j + 3 < sentwithout.length) //Если справа есть три токена
                            {
                                //Ищем cуществующий контекст слова
                                for (ContextInfo contextInfo : ClNearR){
                                    if (contextInfo.word.equals(sentwithout[j + 1].text) && contextInfo.next.containsKey(sentwithout[j+2].text)) {
                                        CiNear = contextInfo;
                                    }
                                    else if (contextInfo.word.equals(sentwithout[j + 1].text)) {
                                        CiNear = contextInfo;
                                        CiFar = new ContextInfo();
                                        CiFar.word = sentwithout[j+2].text;
                                        CiNear.next.put(sentwithout[j + 2].text, CiFar);
                                    }
                                }
                                //добавляем в хешмап третье слово после токена
                                CiNear.next.get(sentwithout[j+2].text).next.put(sentwithout[j+3].text, NullContext);
                            }
                            //Если справа нет трёх токенов, добавляем только два
                            else { CiNear.next.put(sentwithout[j + 2].text, NullContext);}
                            omograph.contextRight.put(sentwithout[j + 1].text, CiNear);
                        }
                        //Если справа нет двух токенов, добавляем только один
                        else { omograph.contextRight.put(sentwithout[j + 1].text, NullContext); }
                    }
                    if (j > 0) //Если слева есть токен
                    {
                        if (j > 1) //Если слева есть два токена
                        {
                            flag = 0;
                            //Ищем cуществующий контекст слова
                            for (ContextInfo contextInfo : ClNearL)
                                if (contextInfo.word.equals(sentwithout[j - 1].text)) {
                                    CiNear = contextInfo;
                                    flag = 1;
                                }
                            //Если его нет создаём новый и добавляем его в список контекстов
                            if (flag == 0) {
                                CiNear = new ContextInfo();
                                CiNear.word = sentwithout[j - 1].text;
                                ClNearL.add(CiNear);
                            }
                            if (j > 2) //Если слева есть три токена
                            {
                                //Ищем cуществующий контекст слова
                                for (ContextInfo contextInfo : ClNearL){
                                    if (contextInfo.word.equals(sentwithout[j - 1].text) && contextInfo.next.containsKey(sentwithout[j-2].text)) {
                                        CiNear = contextInfo;
                                    }
                                    else if (contextInfo.word.equals(sentwithout[j - 1].text)) {
                                        CiNear = contextInfo;
                                        CiFar = new ContextInfo();
                                        CiFar.word = sentwithout[j-2].text;
                                        CiNear.next.put(sentwithout[j - 2].text, CiFar);
                                    }
                                }
                                //добавляем в хешмап третье слово перед токена
                                CiNear.next.get(sentwithout[j-2].text).next.put(sentwithout[j-3].text, NullContext);
                            }
                            //Если слева нет трёх токенов, добавляем только два
                            else { CiNear.next.put(sentwithout[j - 2].text, NullContext); }
                            omograph.contextLeft.put(sentwithout[j - 1].text, CiNear);
                        }
                        //Если слева нет двух токенов, добавляем только один
                        else { omograph.contextLeft.put(sentwithout[j - 1].text, NullContext); }
                    }
                }
            }
        }
        return omograph;
    }
}
