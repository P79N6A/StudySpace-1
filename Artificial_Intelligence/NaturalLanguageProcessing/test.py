import os
import jieba
import jieba.analyse as analyse
from gensim.models.word2vec import Word2Vec

if __name__ == '__main__':
    raw_text = ''
    for file in os.listdir("./input/"):
        if file.endswith(".txt"):
            raw_text += open("./input/" + file, errors='ignore', encoding='UTF-8').read() + '\n\n'
    # raw_text = open('../input/Winston_Churchil.txt').read()
    jieba.load_userdict('./dictionary/userdict.txt')
    seg_list = jieba.cut(raw_text, cut_all=False)
    print(' '.join(seg_list))
    print(' '.join(analyse.extract_tags(raw_text, topK=20, withWeight=False, allowPOS=())))
