package pl.estrix.zwrotpaczek;

public class ViewParam {

    public ViewParam(){
        this.id = 0L;
    }

    public ViewParam(String message) {
        this.message = message;
    }

    public ViewParam(Long id){
        this.id = id;
    }

    private Long id;

    private String message;

    private Long shopNr = 0L;
    private Long returnNr =0L;
    private Long sendNr =0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopNr() {
        return shopNr;
    }

    public void setShopNr(Long shopNr) {
        this.shopNr = shopNr;
    }

    public Long getReturnNr() {
        return returnNr;
    }

    public void setReturnNr(Long returnNr) {
        this.returnNr = returnNr;
    }

    public Long getSendNr() {
        return sendNr;
    }

    public void setSendNr(Long sendNr) {
        this.sendNr = sendNr;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
