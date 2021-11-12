package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger logger = LogManager.getLogger(EntrepriseServiceImpl.class);

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	
	@Override
	public boolean ajouterEntreprise(Entreprise entreprise) {
		try
		{
		logger.info("Ajouter une entreprise");
		logger.debug("Debugging log");
		entrepriseRepoistory.save(entreprise);
		}
		catch(Exception ex)
		{
			logger.error("Erreur ajouter entreprise");
		}
		
		return true;
		
	}
	@Override
	public boolean ajouterDepartement(Departement dep) {
		try{
		deptRepoistory.save(dep);
		logger.info(" departement ajouté)");
    	logger.debug("ajout departement)");
		}
	catch (Exception e) {
		 logger.error("erreur ajout departement");
	}
	
		return true;

	}
	@Override
public int affecterDepartementAEntreprise(int depId, int entrepriseId) {
		
		
		Optional<Entreprise> ent = entrepriseRepoistory.findById(entrepriseId);
		Optional<Departement> dep = deptRepoistory.findById(depId);
    	Entreprise entrepriseManagedEntity ;
    	Departement depManagedEntity ;
    	
    	if (ent.isPresent() && dep.isPresent())
    	{
		 entrepriseManagedEntity = ent.get();
		depManagedEntity = dep.get();
	depManagedEntity.setEntreprise(entrepriseManagedEntity);
		deptRepoistory.save(depManagedEntity);
		try{
		logger.info("affectaté avec succés");
		logger.debug("affectation entreprise");
		
		}
		catch(Exception ex){
			logger.error("Erreur affectation");
		}}
		return depId ; 
    	
	}
	@Override
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		List<String> depNames = new ArrayList<>();
		try{
			Optional<Entreprise> ent = entrepriseRepoistory.findById(entrepriseId);
	    	Entreprise entrepriseManagedEntity ;
	    	if (ent.isPresent())
	    	{

		 entrepriseManagedEntity =  ent.get();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
			logger.info(" liste de tout les departements par entreprise affiché)");
	    	logger.debug("tout les departements par entreprise)");
			
		} } }
	
		catch (Exception e) {
			 logger.error(" liste de tout les departements par entreprise failed");
		}
	    	
		return depNames;
	}
	@Override
	public boolean deleteEntrepriseById(int entrepriseId) {
		try {
			
			Optional<Entreprise> ent = entrepriseRepoistory.findById(entrepriseId);
	    	if (ent.isPresent())
	    	{
		entrepriseRepoistory.delete(ent.get());	
		logger.info("entreprise supprimée");
		logger.debug("suppression entreprise");
		}}
		catch(Exception ex){
			logger.error("Erreur delete entreprise");
		}
		return true ; 
	}
	@Transactional
	public boolean deleteDepartementById(int depId) {
		try{
			Optional<Departement> dep = deptRepoistory.findById(depId);
	    	if (dep.isPresent())
	    	{
		this.deptRepoistory.delete(dep.get());
		logger.info(" departement suprimé avec succés )");
    	logger.debug("suppression departement)");
		}}
		catch (Exception e) {
			 logger.error(" suppression departement failed");
		}
		return true;		
	}
	@Override
	public Entreprise getEntrepriseById(int entrepriseId) {
		Entreprise e = new Entreprise(); 
		try{
			Optional<Entreprise> ent = entrepriseRepoistory.findById(entrepriseId);
	    	if (ent.isPresent())
	    	{
			e = ent.get();
			logger.info("entreprise trouvée");
			logger.debug("recherche entreprise");
			
		}}
		catch(Exception ex){
			logger.error("entreprise introuvable");
		}
		
		return e ; 
	}
	
	
}
