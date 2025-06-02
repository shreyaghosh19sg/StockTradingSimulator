const stocks = {
  'AAPL': { name: 'Apple Inc.', price: 150.00 },
  'GOOGL': { name: 'Alphabet Inc.', price: 2800.00 },
  'AMZN': { name: 'Amazon.com Inc.', price: 3400.00 }
};

let portfolio = {};
let cash = 10000.00;

function updateStocksTable() {
  const tbody = document.getElementById('stocksBody');
  tbody.innerHTML = '';
  for (let symbol in stocks) {
    const stock = stocks[symbol];
    const row = document.createElement('tr');
    row.innerHTML = `<td>${symbol}</td><td>${stock.name}</td><td>${stock.price.toFixed(2)}</td>`;
    tbody.appendChild(row);
  }
}

function updatePortfolioTable() {
  const tbody = document.getElementById('portfolioBody');
  tbody.innerHTML = '';
  let total = cash;
  for (let symbol in portfolio) {
    const quantity = portfolio[symbol];
    const price = stocks[symbol].price;
    total += quantity * price;
    const row = document.createElement('tr');
    row.innerHTML = `<td>${symbol}</td><td>${quantity}</td><td>${price.toFixed(2)}</td>`;
    tbody.appendChild(row);
  }
  document.getElementById('cashBalance').innerText = `Cash Balance: $${cash.toFixed(2)}`;
  document.getElementById('totalValue').innerText = `Total Portfolio Value: $${total.toFixed(2)}`;
}

function showBuyForm() {
  hideForms();
  document.getElementById('buyForm').classList.remove('hidden');
}

function showSellForm() {
  hideForms();
  document.getElementById('sellForm').classList.remove('hidden');
}

function hideForms() {
  document.getElementById('buyForm').classList.add('hidden');
  document.getElementById('sellForm').classList.add('hidden');
  document.getElementById('buySymbol').value = '';
  document.getElementById('buyQuantity').value = '';
  document.getElementById('sellSymbol').value = '';
  document.getElementById('sellQuantity').value = '';
}

function buyStock() {
  const symbol = document.getElementById('buySymbol').value.toUpperCase();
  const quantity = parseInt(document.getElementById('buyQuantity').value);
  if (!stocks[symbol]) {
    alert('Stock symbol not found.');
    return;
  }
  if (isNaN(quantity) || quantity <= 0) {
    alert('Invalid quantity.');
    return;
  }
  const totalCost = stocks[symbol].price * quantity;
  if (cash >= totalCost) {
    cash -= totalCost;
    portfolio[symbol] = (portfolio[symbol] || 0) + quantity;
    updatePortfolioTable();
    hideForms();
    alert(`Purchased ${quantity} shares of ${symbol}`);
  } else {
    alert('Insufficient funds to complete purchase.');
  }
}

function sellStock() {
  const symbol = document.getElementById('sellSymbol').value.toUpperCase();
  const quantity = parseInt(document.getElementById('sellQuantity').value);
  if (!stocks[symbol]) {
    alert('Stock symbol not found.');
    return;
  }
  if (isNaN(quantity) || quantity <= 0) {
    alert('Invalid quantity.');
    return;
  }
  const currentQuantity = portfolio[symbol] || 0;
  if (currentQuantity >= quantity) {
    cash += stocks[symbol].price * quantity;
    portfolio[symbol] = currentQuantity - quantity;
    if (portfolio[symbol] === 0) {
      delete portfolio[symbol];
    }
    updatePortfolioTable();
    hideForms();
    alert(`Sold ${quantity} shares of ${symbol}`);
  } else {
    alert('Insufficient shares to complete sale.');
  }
}

function updatePrices() {
  for (let symbol in stocks) {
    const change = (Math.random() - 0.5) * 10; // Random change between -5 and +5
    stocks[symbol].price = Math.max(0, stocks[symbol].price + change);
  }
  updateStocksTable();
  updatePortfolioTable();
  alert('Market prices have been updated.');
}

// Initialize tables on page load
updateStocksTable();
updatePortfolioTable();
