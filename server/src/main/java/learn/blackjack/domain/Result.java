package learn.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {


    T payload;
    List<String> errorMessages = new ArrayList<>();

//    boolean busted;
//    public boolean isBusted() {
//        return busted;
//    }
//
//    public void setBusted(boolean busted) {
//        this.busted = busted;
//    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void addErrorMessage( String errorMessage ){
        errorMessages.add(errorMessage);
    }

    public List<String> getErrorMessages(){
        //return a copy so the outside world can't mutate errors after
        //they've been added
        return new ArrayList<>(errorMessages);
    }
    public boolean isSuccess(){
        return errorMessages.isEmpty();
    }
}
