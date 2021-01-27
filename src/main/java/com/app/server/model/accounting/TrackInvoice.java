package com.app.server.model.accounting;

import com.app.server.enums.TrackInvoiceState;
import com.app.server.model.audio.Track;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="track_invoice")
public class TrackInvoice {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String trackInvoiceID;

    @Column(name="gross_income")
    private double grossIncome;

    @Column(name="net_income")
    private double netIncome;

    @Column(name="tax_rate")
    private double taxRate;

    @Enumerated(EnumType.STRING)
    @Column(name="invoice_state")
    private TrackInvoiceState trackInvoiceState;

    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="track_fk")
    private Track track;

    public TrackInvoice() {
    }

    public TrackInvoice(double grossIncome, double netIncome, double taxRate, Track track) {
        this.grossIncome = grossIncome;
        this.netIncome = netIncome;
        this.taxRate = taxRate;
        this.track = track;
        this.creationDate = LocalDateTime.now();
        this.trackInvoiceState = TrackInvoiceState.Created;
    }

    public String getTrackInvoiceID() {
        return trackInvoiceID;
    }

    public void setTrackInvoiceID(String trackInvoiceID) {
        this.trackInvoiceID = trackInvoiceID;
    }

    public double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public TrackInvoiceState getTrackInvoiceState() {
        return trackInvoiceState;
    }

    public void setTrackInvoiceState(TrackInvoiceState trackInvoiceState) {
        this.trackInvoiceState = trackInvoiceState;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "TrackInvoice{" +
                "trackInvoiceID='" + trackInvoiceID + '\'' +
                ", grossIncome=" + grossIncome +
                ", netIncome=" + netIncome +
                ", taxRate=" + taxRate +
                ", trackInvoiceState=" + trackInvoiceState +
                ", creationDate=" + creationDate +
                ", track=" + track +
                '}';
    }
}
