package com.thomas.mirakle.dictionary;

class Dict {
    public String word;
    public String word_type;
    public String word_definition;

    public Dict(String word,String word_type,String word_definition) {
        this.word=word;
        this.word_type=word_type;
        this.word_definition=word_definition;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord_type() {
        return word_type;
    }

    public void setWord_type(String word_type) {
        this.word_type = word_type;
    }

    public String getWord_definition() {
        return word_definition;
    }

    public void setWord_definition(String word_definition) {
        this.word_definition = word_definition;
    }
}
