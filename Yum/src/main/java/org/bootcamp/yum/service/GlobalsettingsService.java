/*
 * Copyright (C) 2017 JR Technologies.
 * This file is part of Yum.
 * 
 * Yum is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Yum is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Yum. 
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.bootcamp.yum.service;

import javax.transaction.Transactional;
import org.bootcamp.tasks.ReportEmail;
import org.bootcamp.yum.api.ApiException;
import org.bootcamp.yum.api.ConcurrentModificationException;
import org.bootcamp.yum.api.model.GlobalSettings;
import org.bootcamp.yum.api.model.LastEdit;
import org.bootcamp.yum.data.entity.DailyMenu;
import org.bootcamp.yum.data.entity.Settings;
import org.bootcamp.yum.data.repository.DailyMenuRepository;
import org.bootcamp.yum.data.repository.SettingsRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-04-20T10:12:43.892+03:00")


@Service
public class GlobalsettingsService {
    
    @Autowired
    SettingsRepository settingsRepo;
    
    @Autowired
    ReportEmail reportEmail;

    @Autowired
    DailyMenuRepository dailyMenuRepo;
    
    @Autowired
    EmailService emailService;
    
    public GlobalSettings globalsettingsGet() throws ApiException{
        GlobalSettings globalSettings = new GlobalSettings();        
        Settings settings = settingsRepo.findById(1);
        
        globalSettings.setCurrency(settings.getCurrency());
        globalSettings.setDeadline(settings.getDeadline().toString());
        globalSettings.setDeadlineDays(settings.getDeadlineDays());
        LastEdit lastEdit = new LastEdit();
        lastEdit.setTimeStamp(settings.getLastEdit());
        lastEdit.setVersion(settings.getVersion());
        globalSettings.setLastEdit(lastEdit);
        globalSettings.setNotes(settings.getNotes());
        globalSettings.setPolicy(settings.getPolicy());
        globalSettings.setReportEmail(settings.getReportEmail());
        globalSettings.setTos(settings.getTos());        
        
        return globalSettings;
    }
    
     private boolean oldDeadlinePassed(LocalDate date, int deadlineDays, LocalTime deadline) {
        // Check if order deadline passed based on given date, deadlineDays and deadlineTime (deadline)
        return (date.minusDays(deadlineDays).toLocalDateTime(deadline).compareTo(LocalDateTime.now()) < 0);
    }
    
    @Transactional
    public void globalsettingsPut(GlobalSettings upSettings) throws ApiException{
        Settings settings = settingsRepo.findById(1);
        
        if(upSettings == null)
        {
            throw new ApiException(400, "Bad Request");
        }
        else if(upSettings.getLastEdit().getVersion() != settings.getVersion()) // checks the version. It must be the same
        {
            GlobalSettings globalSettings = new GlobalSettings();
            globalSettings.setCurrency(settings.getCurrency());
            globalSettings.setDeadline(settings.getDeadline().toString());
            globalSettings.setDeadlineDays(settings.getDeadlineDays());
            LastEdit lastEdit = new LastEdit();
            lastEdit.setTimeStamp(settings.getLastEdit());
            lastEdit.setVersion(settings.getVersion());
            globalSettings.setLastEdit(lastEdit);
            globalSettings.setNotes(settings.getNotes());
            globalSettings.setPolicy(settings.getPolicy());
            globalSettings.setReportEmail(settings.getReportEmail());
            globalSettings.setTos(settings.getTos());  
            
            throw new ConcurrentModificationException(409, "Concurrent modification error.", globalSettings);
        }
        else
        {
            boolean changes = false;
            // if there are changes the settings will be updated
            boolean updateDeadline =false;
            LocalTime oldDeadlineTime = settings.getDeadline();
            int oldDeadlineDays = settings.getDeadlineDays();
                      
            String currency = upSettings.getCurrency();
            if(currency != null && !currency.trim().equals(settings.getCurrency())){
                settings.setCurrency(currency);
                changes = true;
            }
            
            String deadLine = upSettings.getDeadline();
            if(deadLine != null && !deadLine.trim().equals(settings.getDeadline().toString())){
                settings.setDeadline(new LocalTime(deadLine));
                changes = true;
                updateDeadline = true;  
                if ( emailService != null){
                    reportEmail.registerSchedule();
                }
            }
            
            Integer deadlineDays = upSettings.getDeadlineDays();
            if(deadlineDays != null && deadlineDays!=settings.getDeadlineDays()){
                settings.setDeadlineDays(deadlineDays);
                changes = true;
                updateDeadline = true;
            }
            
            String notes = upSettings.getNotes(); 
            if(notes != null && !notes.trim().equals(settings.getNotes())){
                settings.setNotes(notes);
                changes = true;
            }
            
            String policy = upSettings.getPolicy();
            if(policy != null && !policy.trim().equals(settings.getPolicy())){
                settings.setPolicy(policy);
                changes = true;
            }
            
            String tos = upSettings.getTos();
            if(tos != null && !tos.trim().equals(settings.getTos())){
                settings.setTos(tos);
                changes = true;
            }
            
            String reportEmail = upSettings.getReportEmail();
            if(reportEmail != null && !reportEmail.trim().equals(settings.getReportEmail())){
                settings.setReportEmail(reportEmail);
                changes = true;
            }
            
            if(!changes)
                throw new ApiException(400, "Bad Request"); // no changes for update
            
 
            // in case of changes to deadline days and/or time, examine if email reports have to be sent
            if(updateDeadline){                 
                int newDeadlineDays = settings.getDeadlineDays();
                LocalTime newDeadlineTime = settings.getDeadline();
                //if new deadline is earlier than old deadline
                if (newDeadlineDays>oldDeadlineDays || (newDeadlineDays==oldDeadlineDays && newDeadlineTime.compareTo(oldDeadlineTime)<0)){
                    // iterate through daily menu dates between today+newDeadlineDays and today+oldDeadlineDays
                    for (int i=oldDeadlineDays; i<=newDeadlineDays; i++) {
                        LocalDate dailyMenuDate = (new LocalDate()).plusDays(i);
                        // if old deadline not passed and new deadline passed and dailyMenu not null, send report email
                        if (!oldDeadlinePassed(dailyMenuDate, oldDeadlineDays, oldDeadlineTime) && settings.deadlinePassed(dailyMenuDate) && dailyMenuRepo.findByDate(dailyMenuDate) != null){
                                //System.out.println(">>>>>>>>>>>>>sending email, date: " + dailyMenuDate);
                                emailService.sendOrderSummary(dailyMenuDate);                                 
                        }
                    }
                    
                }
            }
 
        }        
    }

}
