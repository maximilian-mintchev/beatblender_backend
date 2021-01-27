package com.app.server.services.accounting;

import com.app.server.model.audio.Track;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    public InvoiceService() {
    }

    public double calculateGrossIncome(Track track) {
        return 0.0;
    }

    public double calculateNetIncome(double grossIncome) {
        return 0.0;
    }


}
