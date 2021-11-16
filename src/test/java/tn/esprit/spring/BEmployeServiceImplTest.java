package tn.esprit.spring;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;




@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BEmployeServiceImplTest {
	
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
	Contrat contrat2= new Contrat(dateDebut, "test", 7000);
	String sDate1="2021-10-13";    
	String sDate2="2021-10-22";  
	

	
	
	//add employeeTest
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
	
	//getbyidemployeetest
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
	
	
	//getAllEmployee
	@Test
	public void Testcase_6()
	{
		List<String>  s=es.getAllEmployeNamesJPQL();
		Assertions.assertThat(s.size()).isGreaterThan(0);

	}
	
	
	//getAllEmployeeByEntreprise
	@Test
	public void Testcase_7()
	{
		List<Employe>  s=es.getAllEmployeByEntreprise(entrepriseRepoistory.findById(1).get());
		
		Assertions.assertThat(s.isEmpty());

	}
	 
	  
	//AddContart
	 @Test
	  public void Testcase_8()
			  { 	es.ajouterContrat(contrat);
					Assertions.assertThat(contrat.getReference()).isGreaterThan(0);
			  }
			  
	
	
	//affecterContartToEmployee
	  @Test
		public void Testcase_9()
			{
				es.affecterContratAEmploye(1,1);
				Contrat d =employeRepository.findById(1).get().getContrat(); 

				Assertions.assertThat(d.getReference()).isEqualTo(1);
			}
		
	//affecterEmpToDepartement
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
		
	  
	   //GetSalireMoyByDept
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
  
  
  //DeleteContart
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

	
   //DeleteEmployee
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
      
      
	
	 @Test
		public void Testcase_14()
		{
			
		 
		 
		 
		 
		 
		 
		 
		 
		 
			es.ajouterContrat(contrat);
			contrat2.setReference(2);
		    es.deleteContratById(2);
		    Contrat c = null;

	        Optional<Contrat> optionaContrat = contratRepoistory.findById(10);

	        if(optionaContrat.isPresent()){
	            c = optionaContrat.get();
	        }
	        
	        Assertions.assertThat(c).isNull();
	        
		}
		/* 
		@Test
		public void deleteAllContratJPQLTest() {
			es.deleteAllContratJPQL();
			Assertions.assertThat(contratRepoistory.findAll()).isEmpty();
		}*/
		
}
