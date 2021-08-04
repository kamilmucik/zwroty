package pl.estrix.zwrotpaczek.service;

public class Ean13 {

    private int[] weight = {1,3,1,3,1,3,1,3,1,3,1,3};

    private static Ean13 instance = null;

    public static Ean13 getInstance() {
        if (instance == null) {
            instance = new Ean13();
        }
        return instance;
    }

    public boolean validate(String code) {
        try {
            if (code != null
                    && (code.length() == 13 || code.length() == 8)) {
                int checkSum;

                checkSum = Integer.valueOf(code.substring(code.length() - 1, code.length()));
                int sum = 0;
                for (int i = weight.length - 1; i >= 0; i--) {
                    sum += weight[i] * Character.getNumericValue(code.charAt(i));
                }

                if (((sum + checkSum) % 10) == 0) {
                    return true;    // EAN is ok
                } else {
                    return false;   // no EAN
                }
            }
        }catch (Exception e){

            return false;
        }

        return false;
    }
}
