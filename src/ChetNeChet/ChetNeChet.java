package ChetNeChet;

public class ChetNeChet implements Result {
    int[] arrOfNumbers;
    public ChetNeChet(String numbers){
        String[] numbersToString = numbers.split(",");
        arrOfNumbers = new int[numbersToString.length];
        for (int i = 0; i < numbersToString.length; ++i) {
            String number = numbersToString[i];
            arrOfNumbers[i] = Integer.parseInt(number);
        }
    }

    ChetNeChet(int[] numbers) {
        arrOfNumbers = numbers;
    }

    public String getResult() {
        int chet = 0;
        int neChet = 0;
        for (int i = 0; i < arrOfNumbers.length; ++i) {
            if (arrOfNumbers[i]%2 == 0) chet++;
            else neChet++;
        }
        return "Колличество чётных : " + chet + ", колличество нечётных: " + neChet;
    }
}