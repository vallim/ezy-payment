package com.vallim.payments.api;

import com.vallim.payments.model.Webhook;
import com.vallim.payments.repository.WebhookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/webhooks")
@RestController
public class WebhooksController {

    private final WebhookRepository webhookRepository;

    public WebhooksController(WebhookRepository webhookRepository) {
        this.webhookRepository = webhookRepository;
    }

    @Operation(
            summary = "Lists all registered webhooks",
            description = "Returns all webhook endpoints registered in the system."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of webhooks returned successfully",
            content = @Content(
                    schema = @Schema(implementation = Webhook.class)
            )
    )
    @GetMapping
    public Iterable<Webhook> findAll() {
        return webhookRepository.findAll();
    }

    @Operation(
            summary = "Create a new webhook",
            description = "Creates a new webhook record. This webhook will be notified whenever a new payment is created."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Webhook created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid webhook payload"
    )
    @PostMapping
    public ResponseEntity save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Webhook.class),
                            examples = @ExampleObject(
                                    name = "Webhook example",
                                    value = """
                    {
                      "callback_url": "http://localhost:8080/api-mock/success"
                    }
                    """
                            )
                    )
            )
            @Valid @RequestBody Webhook webhook) {
        webhookRepository.save(webhook);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Remove a webhook",
            description = "Deletes a webhook by its unique identifier."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Webhook deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Webhook not found"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        if (!webhookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        webhookRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
