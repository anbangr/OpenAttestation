/*
 * (copyright) 2012 United States Government, as represented by the 
 * Secretary of Defense.  All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * - Redistributions of source code must retain the above copyright 
 * notice, this list of conditions and the following disclaimer. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the name of the U.S. Government nor the names of its 
 * contributors may be used to endorse or promote products derived from 
 * this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS 
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT 
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY 
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.niarl.sal.webservices.hisWebService.server;

import gov.niarl.hisAppraiser.hibernate.dao.*;
import gov.niarl.hisAppraiser.hibernate.domain.AttestRequest;
import gov.niarl.sal.webservices.hisWebService.server.domain.ActionConverter;
import gov.niarl.sal.webservices.hisWebService.server.domain.ActionDelay;
import gov.niarl.sal.webservices.hisWebService.server.domain.ActionDelay.Action;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

/**
 * The HisPollingWebService answers clients polls with their next actions.  
 * @author syelama
 * @version Crossbow
 *
 */
@WebService
public class HisPollingWebService {
	private static Logger logger = Logger.getLogger(HisPollingWebService.class);

	/**
	 * This function returns the pending action to machines and resets 
	 * the fields in the database.
	 * @param machineName Name of the machine
	 * @return An object containing information about the action to be taken.      
	 */
	@WebResult(name = "nextAction")
	public ActionDelay getNextAction(@WebParam(name = "machineName") String machineName) {
		logger.debug("getNextAction called with arguments machineName=" + machineName);
		Action action = Action.DO_NOTHING;
		String args = "";
		AttestDao attestDao = new AttestDao();
		AttestRequest attestRequest = attestDao.getFirstRequest(machineName);
		action = ActionConverter.getActionFromInt(attestRequest.getNextAction() == null ? ActionConverter.getIntFromAction(Action.DO_NOTHING) : attestRequest.getNextAction());
		if (attestRequest.getId()!= null){
			attestRequest.setNextAction(ActionConverter.getIntFromAction(Action.DO_NOTHING));
			attestRequest.setIsConsumedByPollingWS(true);
			attestDao.updateRequest(attestRequest);
		}
		return new ActionDelay(action, HisSystemConstants.DEFAULT_DELAY, args);
	}
	
	/**
	 * Below is the original polling code from NIARL.
	 */
//		Action action = Action.DO_NOTHING;
//		String args = "";
//		try {
//			HibernateUtilHis.beginTransaction();
//
//			HisMachineCertDao hisMachineCertDao = new HisMachineCertDao();
//			MachineCert machineCert = hisMachineCertDao.getMachineCert(machineName);
//			action = ActionConverter.getActionFromInt(machineCert.getNextAction() == null ? ActionConverter.getIntFromAction(Action.DO_NOTHING) : machineCert.getNextAction());
//			args = machineCert.getPollArgs() == null ? "" : machineCert.getPollArgs();
//
//			machineCert.setNextAction(ActionConverter.getIntFromAction(Action.DO_NOTHING));
//			machineCert.setLastPoll(new Date());
//			machineCert.setPollArgs("");
//
//			HibernateUtilHis.commitTransaction();
//		} catch (Exception e) {
//			HibernateUtilHis.rollbackTransaction();
//			e.printStackTrace();
//			throw new RuntimeException(e.toString());
//		} finally {
//			HibernateUtilHis.closeSession();
//		}
//		return new ActionDelay(action, HisSystemConstants.DEFAULT_DELAY, args);
//	}
}
