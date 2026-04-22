package com.ver.ssms.controller;

import com.ver.ssms.dto.BodyResponse;
import com.ver.ssms.dto.stock.incoming.CreateStock;
import com.ver.ssms.dto.stock.incoming.UpdateStock;
import com.ver.ssms.model.Stock;
import com.ver.ssms.service.StockService;
import com.ver.ssms.utility.statuses.StockRequestStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ssms/api/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService){ this.stockService = stockService;}

    @GetMapping("/index")
    public ResponseEntity<BodyResponse<?>> index(){
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of(
                                "result", stockService.getALl(),
                                "request_status", StockRequestStatus.STOCK_FETCHED
                        )
                )
        );
    }

    @PostMapping("/new")
    public ResponseEntity<BodyResponse<?>> newStock(@Valid @RequestBody CreateStock createStock){
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of(
                                "result", stockService.newStockTransaction(new Stock(createStock)),
                                "request_status", StockRequestStatus.STOCK_ADDED
                        )
                )
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<BodyResponse<?>> editStock(@Valid @RequestBody UpdateStock updateStock) {
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of(
                                "result",stockService.updateStock(new Stock(updateStock)),
                                "request_status", StockRequestStatus.STOCK_UPDATED
                        )
                )
        );
    }
    @DeleteMapping("/delete")
    public ResponseEntity<BodyResponse<?>> removeStock(@RequestBody Map<String, List<String>> deleteListOfId){
        List<String> idListToDelete = deleteListOfId.get("stockId");
        return ResponseEntity.ok(
                BodyResponse.success(
                        Map.of("result", stockService.stockToZero(idListToDelete))
                )
        );
    }

}
