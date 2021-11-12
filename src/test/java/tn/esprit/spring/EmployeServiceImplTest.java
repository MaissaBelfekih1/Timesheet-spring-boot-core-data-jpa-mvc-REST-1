package tn.esprit.spring;


import static org.junit.Assert.assertFalse;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;



@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	
	
	@Autowired
	EmployeServiceImpl es=new EmployeServiceImpl();
	
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	MissionRepository mr;
	@Autowired
	TimesheetRepository timesheetRepository;
	private Date dateDebut = new Date();
	Employe employe = new Employe("maissa", "belfekih", "maissa@esprit.tn",true ,Role.CHEF_DEPARTEMENT);
	Contrat contrat= new Contrat(dateDebut, "test", 7000);
	String sDate1="2021-10-13";    
	String sDate2="2021-10-22";  


	//add employee
	@Test
	public void Testcase_1()
	{  	es.ajouterEmploye(employe);
		Assertions.assertThat(employe.getId()).isGreaterThan(0);
	}
    
	//getallEmployee
	@Order(2)
	@Test
	public void Testcase_2() {
    List<Employe> e =es.getAllEmployes();
    Assertions.assertThat(e.size()).isGreaterThan(0);
    }
	
	//getbyidemployee
	@Order(3)
	@Test
    public void Testcase_3()
	  {  String ch = es.getEmployePrenomById(1);
		Assertions.assertThat(ch).isEqualTo("belfekih");

	  }
	  
	//getNombreEmployeJPQLTest

	@Test
	public void Testcase_4(){
	int s=	es.getNombreEmployeJPQL();
	Assertions.assertThat(s).isGreaterThan(0);
	}
	
	//updateemployee1
	
/*	@Test
    public void Testcase_5(){

		es.mettreAjourEmailByEmployeId("Ghassen2@outlook.fr", 1);

		Employe employeeUpdated=employeRepository.findById(1).get();
        Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("Ghassen2@outlook.fr");

    }
    
	*/

	@Test
	public void Testcase_5()
	{
		es.mettreAjourEmailByEmployeIdJPQL("Ghassen2@outlook.fr", 1);
	
		Employe employeeUpdated=employeRepository.findById(1).get();
	    Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("Ghassen2@outlook.fr");
	} 
	
	

	@Test
	public void Testcase_6()
	{
		List<String>  s=es.getAllEmployeNamesJPQL();
		Assertions.assertThat(s.size()).isGreaterThan(0);

	}
	
	
	
	@Test
	public void Testcase_7()
	{
		List<Employe>  s=es.getAllEmployeByEntreprise(entrepriseRepoistory.findById(1).get());
		
		Assertions.assertThat(s.isEmpty());

	}
	 
	  
	
	 @Test
	  public void Testcase_8()
			  { 	es.ajouterContrat(contrat);
					Assertions.assertThat(contrat.getReference()).isGreaterThan(0);
			  }
			  
	
	
	
	  @Test
		public void Testcase_9()
			{
				es.affecterContratAEmploye(1,1);
				Contrat d =employeRepository.findById(1).get().getContrat(); 

				Assertions.assertThat(d.getReference()).isEqualTo(1);
			}
		

	  @Test
			public void Testcase_10()
			{
				es.affecterEmployeADepartement(1, 1);
				List<Departement> d =employeRepository.findById(1).get().getDepartements(); 

				Assertions.assertThat(d.isEmpty()).isFalse();
			}
		   
		
		
		
		
	
	  
	/*	@Order(12)
		@Test
		public void getSalaireByEmployeIdJPQLTest() {
			float i =es.getSalaireByEmployeIdJPQL(1);
			System.out.println(i);
			Assertions.assertThat(i).isGreaterThan(0);

		}
      */
		
	  
	
		@Test
		public void Testcase_11() {
			Double e= es.getSalaireMoyenByDepartementId(1);
			Assertions.assertThat(e).isGreaterThan(0);

		
		}
		
		

	
  
	/*@Order(12)
	
	public void desaffecterEmployeDuDepartementTest()
	{
		es.desaffecterEmployeDuDepartement(2, 1);
		List<Departement> d =employeRepository.findById(2).get().getDepartements(); 
		
		Assertions.assertThat(d.isEmpty()).isTrue();

	}*/
  
  
 
    @Test
	public void Testcase_12()
	{
	    es.deleteContratById(1);
	    Contrat c = null;

        Optional<Contrat> optionaContrat = contratRepoistory.findById(1);

        if(optionaContrat.isPresent()){
            c = optionaContrat.get();
        }
        
        Assertions.assertThat(c).isNull(); }

	
   
	 @Test
	 public void Testcase_13()
	 {
		    es.deleteEmployeById(1);
	        Employe employee1 = null;

	        Optional<Employe> optionalEmployee = employeRepository.findById(1);

	        if(!optionalEmployee.isPresent()){
	            employee1 = optionalEmployee.get();
	            Assertions.assertThat(employee1).isNull();
	        }
	        
	        

	 }
       
	/* 
		@Test
		public void deleteContratByIdTest()
		{
		    es.deleteContratById(1);
		    Contrat c = null;

	        Optional<Contrat> optionaContrat = contratRepoistory.findById(10);

	        if(optionaContrat.isPresent()){
	            c = optionaContrat.get();
	        }
	        
	        Assertions.assertThat(c).isNull();

			
		}
		@Test
		public void deleteAllContratJPQLTest() {
			es.deleteAllContratJPQL();
			Assertions.assertThat(contratRepoistory.findAll()).isEmpty();
		}*/
		
}
