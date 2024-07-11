import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SodaMachine {
    private int insertedMoney;
    private Map<String, Integer> products;
    private Map<String, Integer> consecutivePurchases;
    private int promotionBudget;
    private int dailyBudget;
    private double winRate;
    private Random random;

    public SodaMachine() {
        this.insertedMoney = 0;
        this.products = new HashMap<>();
        this.products.put("Coke", 10000);
        this.products.put("Pepsi", 10000);
        this.products.put("Soda", 20000);
        this.consecutivePurchases = new HashMap<>();
        this.consecutivePurchases.put("Coke", 0);
        this.consecutivePurchases.put("Pepsi", 0);
        this.consecutivePurchases.put("Soda", 0);
        this.dailyBudget = 50000;
        this.promotionBudget = dailyBudget;
        this.winRate = 0.1;  // 10% chance for free product
        this.random = new Random();
    }

    public boolean insertMoney(int amount) {
        if (amount == 10000 || amount == 20000 || amount == 50000 || amount == 100000 || amount == 200000) {
            this.insertedMoney += amount;
            return true;
        }
        return false;
    }

    public String selectProduct(String product) {
        if (products.containsKey(product)) {
            int price = products.get(product);
            int consecutiveCount = consecutivePurchases.get(product);

            if (consecutiveCount == 2) { // Khi số lần mua liên tiếp là 2 (lần mua thứ ba)
                if (random.nextDouble() <= winRate && promotionBudget >= price) {
                    price = 0;  // Sản phẩm miễn phí
                    promotionBudget -= products.get(product);
                    consecutivePurchases.put(product, 0);  // Reset sau khi nhận sản phẩm miễn phí
                    return "Congratulations! You received a free " + product + ".";
                } else {
                    consecutivePurchases.put(product, 0); // Đặt lại sau lần thứ ba không trúng thưởng
                }
            } else {
                consecutivePurchases.put(product, consecutiveCount + 1); // Tăng số lần mua liên tiếp
            }

            if (insertedMoney >= price) {
                insertedMoney -= price;
                return "Product: " + product + ", Change: " + insertedMoney + " VND";
            } else {
                return "Not enough money inserted";
            }
        } else {
            return "Product not available";
        }
    }

    //Cancel request trả lại tiền
    public int cancelRequest() {
        int refund = insertedMoney;
        insertedMoney = 0;
        resetConsecutivePurchases();
        return refund;
    }

    //Reset số lần mua liên tiếp
    private void resetConsecutivePurchases() {
        for (String key : consecutivePurchases.keySet()) {
            consecutivePurchases.put(key, 0);
        }
    }

    public void endDay() {
        if (promotionBudget > 0) {
            winRate = Math.min(1.0, winRate * 1.5); // Tăng tỷ lệ trúng thưởng lên 50% nhưng không quá 100%
        } else {
            winRate = 0.1; // Reset về 10% nếu dùng hết ngân sách
        }
        promotionBudget = dailyBudget;
        resetConsecutivePurchases();
        System.out.println("Day ended. Win rate is now: " + winRate);
    }
}
