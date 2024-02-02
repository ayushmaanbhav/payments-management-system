package com.ayushmaanbhav.ledger.api.transaction;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionDto;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("transaction")
public interface TransactionApi {

    @PostMapping
    void recordTransaction(@NonNull @RequestBody TransactionDto request) throws ApiException;
}
