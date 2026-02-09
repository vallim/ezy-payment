package com.vallim.payments.api;

import com.vallim.payments.model.Payment;
import com.vallim.payments.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RequestMapping("/payments")
@RestController
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(
            summary = "Create a new payment",
            description = "Creates a payment record in the system and notifies all existing webhooks with the payment content."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Payment created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request"
    )
    @PostMapping
    public ResponseEntity save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Payment.class),
                            examples = @ExampleObject(
                                    name = "Payment example",
                                    value = """
                {
                  "first_name": "John",
                  "last_name": "Doe",
                  "zip_code": "12345",
                  "card_number": "4111111111111111"
                }
                """
                            )
                    )
            )
            @RequestBody Payment payment) {
        paymentService.save(payment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Lists all payments")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Payment> list() {
        return paymentService.findAll();
    }
}
