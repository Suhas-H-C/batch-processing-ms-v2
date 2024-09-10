package com.per.data.proessor;

import com.per.data.entity.PortFolioDetails;
import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;

public class PortfolioDetailProcessor implements ItemProcessor<PortFolioDetails, PortFolioDetails> {


    @Override
    public PortFolioDetails process(PortFolioDetails portFolioDetails) throws Exception {
        portFolioDetails.setPortfolio(portFolioDetails.getPortfolio().toLowerCase(Locale.ENGLISH));
        return portFolioDetails;
    }
}
