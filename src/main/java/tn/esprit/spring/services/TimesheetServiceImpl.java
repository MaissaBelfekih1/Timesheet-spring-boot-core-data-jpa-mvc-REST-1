package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	
	private static final Logger logger = LogManager.getLogger(TimesheetServiceImpl.class);
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	@Override
public boolean ajouterMission(Mission mission) 
	{	
		try
		{
			logger.info("Ajouter une mission donnée");
			missionRepository.save(mission);
			logger.info("Mission ajoutée");
			return true;
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
			return false;
	}
	@Override
	public int affecterMissionADepartement(int missionId, int depId) {
		
		
		logger.info("Affecter Mission A Departement");
		
		logger.debug("Récupérer mission selon id ");
		Optional<Mission> mission  = missionRepository.findById(missionId);
		//Mission mission = missionRepository.findById(missionId).get() ;
		logger.debug("Mission récupérée ");
		
		logger.debug("Récupérer departement selon id ");
		Optional<Departement> dep  = deptRepoistory.findById(depId);
		//Departement dep = deptRepoistory.findById(depId).get();
		logger.debug("departement récupéré");
		if(mission.isPresent() && dep.isPresent())
		{
		try
		{
			
		logger.debug("enregistrer le departement récupéré a la mission récupérée ");
		mission.get().setDepartement(dep.get());
		logger.debug("enregistrement valide");
		
		logger.debug("enregistrer les modifications de la mission récupérée");
		missionRepository.save(mission.get());
		logger.debug("enregistrement valide");
		
		logger.info("Mission affectée");
			
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return mission.get().getId();
		}
		return -1;
	}
	@Override
	public boolean ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		logger.info("Ajouter timesheet");
		
		logger.debug("Nouveau instance du clé primaire timesheetpk");
		TimesheetPK timesheetPK = new TimesheetPK();
		logger.debug("Ajouter les paramétres dans l'instance");
		
		logger.debug("Ajouter la date debut dans le clé primaire composé timesheetpk");
		timesheetPK.setDateDebut(dateDebut);
		logger.debug("Date debut ajoutée");
		
		logger.debug("Ajouter la date fin dans le clé primaire composé timesheetpk");
		timesheetPK.setDateFin(dateFin);
		logger.debug("Date fin ajoutée");
		
		logger.debug("Ajouter un employe dans le clé primaire composé timesheetpk");
		timesheetPK.setIdEmploye(employeId);
		logger.debug("Id employe ajouté");
		
		logger.debug("Ajouter une mission dans le clé primaire composé timesheetpk");
		timesheetPK.setIdMission(missionId);
		logger.debug("Id mission ajouté");
		
		logger.debug("Nouveau instance du timesheet");
		Timesheet timesheet = new Timesheet();
		logger.debug("Ajouter le clé primaire au timesheet");
		
		timesheet.setTimesheetPK(timesheetPK);
		logger.debug("Ajouter la validité de timesheet");
		timesheet.setValide(false); //par defaut non valide
		
		try
		{
		logger.debug("Enregistrement...");
		timesheetRepository.save(timesheet);
		logger.info("Timesheet ajouté");
	}
		
		catch(Exception ex)
		{
			logger.error(ex);
		}
		return true;
	}
	
	
	
	
	
	
	
	@Override
	public int validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		logger.info("In valider Timesheet");
		logger.debug("get validateur by id");
		Optional<Employe> validateur = employeRepository.findById(validateurId);
		logger.debug("get mission by id");
		Optional<Mission> mission  = missionRepository.findById(missionId);
		 if(validateur.isPresent() && mission.isPresent())
		 {
		if(!validateur.get().getRole().equals(Role.CHEF_DEPARTEMENT)){
			logger.info("l'employe doit etre chef de departement pour valider une feuille de temps !");
			return 0;
		}
		boolean chefDeLaMission = false;
		logger.debug("validateur n'est pas  chef departement = false");
		for(Departement dep : validateur.get().getDepartements()){
			if(dep.getId() == mission.get().getDepartement().getId()){
				chefDeLaMission = true;
				logger.debug("validateur est le chef departement = true");
				break;
			}
		}
		if(!chefDeLaMission){
			logger.info("l'employe doit etre chef de departement de la mission en question");
			return 0;	
		}
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		logger.debug("creation du timesheetPK");
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		logger.debug("recuperation du timesheet par timehseetPK");
		timesheet.setValide(true);
		timesheetRepository.save(timesheet);
		logger.info("timesheet was validated");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		logger.info("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		return 1;
	}
		 return 0;
		 }
	
	
	
	@Override
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		logger.info("in find all missions method");
		List<Mission> missions = new ArrayList<>();
		try{
			logger.debug("missions recuperation");
			missions = timesheetRepository.findAllMissionByEmployeJPQL(employeId); 
		}
		catch(Exception e){
			logger.error("Exception :"+e);
		}
		return missions;
	}
	
	@Override
	public List<Employe> getAllEmployeByMission(int missionId) {
		logger.info("in method get all employees");
		List<Employe> employees = new ArrayList<>();
		try{
			logger.debug("employees recuperation");
			employees =timesheetRepository.getAllEmployeByMission(missionId); 
		}
		catch(Exception e){
			logger.error("Exception : "+e);
		}
		return employees;
	}


}
