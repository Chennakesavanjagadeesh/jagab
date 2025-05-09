package com.example.shopping.Services;

import com.example.shopping.Entities.PaymentDetails;

public interface PaymentService {
	
	    PaymentDetails findPaymentByTransactionId(String transactionId);
	


}
