package Leet.Solutions;

import java.util.HashMap;

public class AuthenticationManager {

    HashMap<String, Integer> tokens;
    int expTime;

    public AuthenticationManager(int timeToLive) {
        expTime = timeToLive;
        tokens = new HashMap<>();
    }

    public void generate(String tokenId, int currentTime) {
        tokens.put(tokenId, currentTime);
    }

    public void renew(String tokenId, int currentTime) {
        if(!tokens.containsKey(tokenId)) return;
        if(currentTime-tokens.get(tokenId)<expTime){
            tokens.put(tokenId, currentTime);
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        int res= 0;
        for(int vals: tokens.values()){
            if(currentTime-vals<expTime) res++;
        }
        return res;
    }
}

