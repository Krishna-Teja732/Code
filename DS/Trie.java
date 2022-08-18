package DS;

import java.util.*;

public class Trie {
    Trie[] child;
    String val;

    public Trie(){
        child = new Trie[26];
        val = null;
    }

    public Trie(String val){
        child = null;
        this.val = val;
    }

    private void add(char[] val, int offset){
        StringBuilder res = new StringBuilder();
        if(offset==val.length){
            this.val = new String(val);
        }
        else if(child[val[offset]-'a']==null){
            res.append(val);
            child[val[offset]-'a'] = new Trie(res.toString());
        }
        else if(child[val[offset]-'a'].child==null){
            child[val[offset]-'a'].child = new Trie[26];
            String temp = child[val[offset]-'a'].val;
            child[val[offset]-'a'].val = null;
            child[val[offset]-'a'].add(val, offset+1);
            child[val[offset]-'a'].add(temp.toCharArray(), offset+1);
        }
        else{
            child[val[offset]-'a'].add(val, offset+1);
        }
    }

    public void addString(String val){
        add(val.toCharArray(), 0);
    }

    private void traverse(List<String> res){
        if(child==null) {
            res.add(val);
            return;
        }
        if(val!=null) res.add(val);
        for(Trie temp:child){
            if(temp!=null) temp.traverse(res);
        }
    }

    private void traverse(List<String> res, int limit){
        if(res.size()==limit) return;
        if(child==null) {
            res.add(val);
            return;
        }
        else if(val!=null) res.add(val);
        for(Trie temp:child){
            if(temp!=null) temp.traverse(res, limit);
        }
    }

    public List<String> getValues(){
        List<String> res = new ArrayList<>();
        traverse(res);
        return res;
    }

    public List<String> getValues(int limit){
        List<String> res = new ArrayList<>();
        traverse(res, limit);
        return res;
    }

    public List<String> getValues(String prefix){
        Trie temp = this;
        for(char ch: prefix.toCharArray()){
            temp = temp.child[ch-'a'];
            if(temp==null) break;
        }
        List<String> res = new ArrayList<>();
        if(temp!=null) {
            temp.traverse(res);
        }
        return res;
    }

    public List<String> getValues(String prefix, int limit){
        Trie temp = this;
        for(char ch: prefix.toCharArray()){
            if(temp.child==null) break;
            temp = temp.child[ch-'a'];
            if(temp==null) break;
        }
        List<String> res = new ArrayList<>();
        if(temp!=null) {
            temp.traverse(res, limit);
        }
        return res;
    }
}
