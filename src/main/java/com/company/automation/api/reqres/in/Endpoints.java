package com.company.automation.api.reqres.in;

import com.company.automation.api.setup.HostNames;
import com.company.automation.api.setup.BaseEndpoints;

import java.util.UUID;

public class Endpoints extends BaseEndpoints {

	public final GetAdEventControllerEndpoints getAdEventsController;
	public final GetReceiptDetailsControllerEndpoints getReceiptDetailsController;
	public final PostPPNoCountryControllerEndpoints postPPNoCountryController;
	// public final PostPlaceControllerEndpoints postPlaceController;

	public Endpoints(HostNames hostNames) {
		super(hostNames);
		getAdEventsController = new GetAdEventControllerEndpoints();
		getReceiptDetailsController = 	new GetReceiptDetailsControllerEndpoints();
		postPPNoCountryController = new PostPPNoCountryControllerEndpoints();
		// postPlaceController = new PostPlaceControllerEndpoints();
	}

	public class GetAdEventControllerEndpoints {
		public final String ad = "https://dev.pvdalext.jnj.com/pvdalpapi/api/events/ae?endDate=2020-11-06T08:02:28.497000000&startDate=2020-10-29T07:59:28.497000000&limit=50&offset=0&attachmentCriteria=true";
		
		public final String getEdData() {
			return ad;
		}

	}
	
	public class GetReceiptDetailsControllerEndpoints {
		public final String allReceipts = "https://dev.pvdalext.jnj.com/dupsearchapi/api/receipts?RECEIPT_NO=RCT8817015";
		
		public final String getallReceiptsData() {
			return allReceipts;
		}

	}

	public class PostPPNoCountryControllerEndpoints {
		public final String ppnc = "https://dev.pvdalext.jnj.com/dupsearchapi/api/dupsearch";
		
		public final String postPPNcountryData() {
			return ppnc;
		}

	}
}
