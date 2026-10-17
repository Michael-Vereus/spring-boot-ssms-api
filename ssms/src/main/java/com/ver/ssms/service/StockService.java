package com.ver.ssms.service;

import com.ver.ssms.dto.stock.outrepo.StockSummaryMapping;
import com.ver.ssms.model.Stock;
import com.ver.ssms.repo.StockRepository;
import com.ver.ssms.utility.statuses.StockRequestStatus;
import com.ver.ssms.utility.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings("ALL")
@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository){ this.stockRepository = stockRepository;}

    public List<StockSummaryMapping> getALl(){ return stockRepository.getCurrentStock();}

    public Stock getLatestStockTransactionById(String stockId){
        return stockRepository.findById(stockId).orElse(null);
    }

    @Transactional
    public Stock newStockTransaction(Stock stock){
        if (stock.getType() == TransactionType.OUT ||
                stock.getType() == TransactionType.TRANSFERRED) {
            throw new RuntimeException("New Stock must be labeled as IN transaction");
        }
        return saveTransaction(stock);
    }

    // to myself in the future CHECK THIS SHIT CUZ I THINK THERES SUMTHIN BUG? IDK JS CHECK IT
    @Transactional
    public boolean updateStock(Stock stock){
        // ps its basically balancing out and adding a new transaction not really updating a transaction.
        // But naming it update stock sound easier to remember
        // retrieving full stock information based on its id.
        Stock latestTransaction = getLatestStockTransactionById(stock.getStockId());
        if (latestTransaction == null) throw new AssertionError();

        // need to check the quantity if user wants to decrease the stock since a negative quantity isn't allowed

        // i prefer to call this early so the repo don't have to do multiple times of query to the db, ps : i dont know about caching at this stage hence why i resort to this method
        Long quantityStockOnHand = stockRepository.getQuantityOnHand(stock.getItemId(), stock.getStorageId());

        if (checkItemAndStorageId(stock, latestTransaction)){
            if (stock.getType().equals(TransactionType.OUT)){
                if (quantityStockOnHand < stock.getQuantity()){
                    throw new IllegalArgumentException("Insufficient Stock on hand " + quantityStockOnHand + ", requested" + stock.getQuantity());
                }
            }
            stock.setStockId(stock.generateHexId());
            saveTransaction(stock);
            return true;
        }

        balanceOut(stock, latestTransaction);
        return true;
    }

    @Transactional
    public Map<String,Object> stockToZero(List<String> idLisToDelete){
        Map<String, Object> requestTrackRecord = new HashMap<>();
        for (String id : idLisToDelete){
            Optional<Stock> stock = stockRepository.findById(id);
            Stock stockFound = stock.get();

            if (stockFound.getType().equals(TransactionType.TRANSFERRED)){
                requestTrackRecord.put(id, new IllegalArgumentException("Stock Has been transfered, cannot delete!").getMessage());
                continue;
            }

            Long quantity = stockRepository.getQuantityOnHand(stockFound.getItemId(), stockFound.getStorageId());
            System.out.println(quantity);

            if (quantity <= 0){
                requestTrackRecord.put(id, new IllegalArgumentException("Current Stock Quantity is at zero, can't proceed !").getMessage());
                continue;
            }

            stockRepository.save(Stock.deleteStock(stockFound, quantity));
            requestTrackRecord.put(id, StockRequestStatus.STOCK_DELETED);
        }
        return requestTrackRecord;
    }

    // THIS ONE TOO NIGGA
    @Transactional
    private void balanceOut(Stock incomingStock, Stock latestTransaction){

        if (incomingStock.getType() != TransactionType.IN){
            throw new IllegalArgumentException(
                    "Cannot perform a transfer with type OUT nor TRANSFERRED. " +
                            "Use IN when moving stock to a different item storage."
            );
        }

        Stock stockOut = Stock.outStockObj(latestTransaction);
        stockOut.setType(TransactionType.TRANSFERRED);
        saveTransaction(stockOut);
        saveTransaction(incomingStock);
    }

    @Transactional
    private Stock saveTransaction(Stock stock){
        return stockRepository.save(stock);
    }


    private boolean checkItemAndStorageId(Stock stock, Stock latestTransaction){
        // check if the storage id and the item id still the same or not
        return latestTransaction.getStorageId().equalsIgnoreCase(stock.getStorageId() ) && latestTransaction.getItemId().equalsIgnoreCase(stock.getItemId());
    }
//    ignroe func
//    private Exception checkQuantityRequest(int totalQuantity, int requestQuantity ){
//
//    }
}
