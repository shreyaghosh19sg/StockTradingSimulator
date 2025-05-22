import java.util.*;

public class SiStockTradingmulator {

    // Represents a stock with symbol, name, and price
    static class Stock {
        private String symbol;
        private String name;
        private double price;

        public Stock(String symbol, String name, double price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    // Manages the market with a collection of stocks
    static class Market {
        private Map<String, Stock> stocks = new HashMap<>();

        public void addStock(Stock stock) {
            stocks.put(stock.getSymbol(), stock);
        }

        public void updatePrices() {
            Random rand = new Random();
            for (Stock stock : stocks.values()) {
                double change = (rand.nextDouble() - 0.5) * 10; // Random change between -5 and +5
                stock.setPrice(Math.max(0, stock.getPrice() + change));
            }
        }

        public Stock getStock(String symbol) {
            return stocks.get(symbol);
        }

        public Collection<Stock> getAllStocks() {
            return stocks.values();
        }
    }

    // Represents the user's portfolio with holdings and cash balance
    static class Portfolio {
        private Map<String, Integer> holdings = new HashMap<>();
        private double cash;

        public Portfolio(double initialCash) {
            this.cash = initialCash;
        }

        public boolean buyStock(Stock stock, int quantity) {
            double totalCost = stock.getPrice() * quantity;
            if (cash >= totalCost) {
                holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
                cash -= totalCost;
                return true;
            }
            return false;
        }

        public boolean sellStock(Stock stock, int quantity) {
            int currentQuantity = holdings.getOrDefault(stock.getSymbol(), 0);
            if (currentQuantity >= quantity) {
                holdings.put(stock.getSymbol(), currentQuantity - quantity);
                cash += stock.getPrice() * quantity;
                return true;
            }
            return false;
        }

        public double getTotalValue(Market market) {
            double total = cash;
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                Stock stock = market.getStock(entry.getKey());
                if (stock != null) {
                    total += stock.getPrice() * entry.getValue();
                }
            }
            return total;
        }

        public void printPortfolio(Market market) {
            System.out.println("Cash Balance: $" + String.format("%.2f", cash));
            System.out.println("Holdings:");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                Stock stock = market.getStock(entry.getKey());
                if (stock != null) {
                    System.out.println(entry.getKey() + ": " + entry.getValue() + " shares @ $" + String.format("%.2f", stock.getPrice()));
                }
            }
            System.out.println("Total Portfolio Value: $" + String.format("%.2f", getTotalValue(market)));
        }
    }

    // Main method to run the simulator
    public static void main(String[] args) {
        Market market = new Market();
        market.addStock(new Stock("AAPL", "Apple Inc.", 150.0));
        market.addStock(new Stock("GOOGL", "Alphabet Inc.", 2800.0));
        market.addStock(new Stock("AMZN", "Amazon.com Inc.", 3400.0));

        Portfolio portfolio = new Portfolio(10000.0);
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Stock Trading Simulator!");
        System.out.println("Available commands: view, buy, sell, portfolio, update, exit");

        while (true) {
            System.out.print("\nEnter command: ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "view":
                    System.out.println("Market Stocks:");
                    for (Stock stock : market.getAllStocks()) {
                        System.out.println(stock.getSymbol() + " - " + stock.getName() + ": $" + String.format("%.2f", stock.getPrice()));
                    }
                    break;
                case "buy":
                    System.out.print("Enter stock symbol: ");
                    String buySymbol = scanner.nextLine().trim().toUpperCase();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        System.out.print("Enter quantity to buy: ");
                        int buyQuantity = Integer.parseInt(scanner.nextLine().trim());
                        if (portfolio.buyStock(buyStock, buyQuantity)) {
                            System.out.println("Purchased " + buyQuantity + " shares of " + buySymbol);
                        } else {
                            System.out.println("Insufficient funds to complete purchase.");
                        }
                    } else {
                        System.out.println("Stock symbol not found.");
                    }
                    break;
                case "sell":
                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = scanner.nextLine().trim().toUpperCase();
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        System.out.print("Enter quantity to sell: ");
                        int sellQuantity = Integer.parseInt(scanner.nextLine().trim());
                        if (portfolio.sellStock(sellStock, sellQuantity)) {
                            System.out.println("Sold " + sellQuantity + " shares of " + sellSymbol);
                        } else {
                            System.out.println("Insufficient shares to complete sale.");
                        }
                    } else {
                        System.out.println("Stock symbol not found.");
                    }
                    break;
                case "portfolio":
                    portfolio.printPortfolio(market);
                    break;
                case "update":
                    market.updatePrices();
                    System.out.println("Market prices have been updated.");
                    break;
                case "exit":
                    System.out.println("Exiting the simulator. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}
