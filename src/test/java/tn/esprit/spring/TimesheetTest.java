package tn.esprit.spring;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.hamcrest.core.Is;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.TimesheetServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.DEFAULT)
public class TimesheetTest {

	@Autowired
	TimesheetServiceImpl time;
	@Autowired
	EmployeServiceImpl empserv;
	@Autowired
	DepartementRepository deprep;
	@Autowired
	EmployeRepository emprep;
	@Autowired
	TimesheetRepository timesheetrepo;
	
	Mission m = new Mission("deplacement","deplacement");
	 
		
	    Date date = new Date(1633824000000l);
		Date date2 = new Date(1633824000000l);
		
		//test ajouter mission
		@Test
		public void Testcase_1()
		{
			assertEquals(true, time.ajouterMission(m));
			
		}
		//test affecter mission a departement
		@Test
		public void Testcase_2()
		{
			assertEquals(1,time.affecterMissionADepartement(1, 1));
		}
		//test ajouter timesheet
		@Test
		public void Testcase_3()
		{
			assertEquals(true,time.ajouterTimesheet(1, 2, date, date2));
		}
		
		//test valider timesheet
		@Test
		 public void Testcase_4() throws ParseException
		{
			Employe e = new Employe("ahmed","rais","ahmedrais@gmail.com",false,Role.TECHNICIEN);
			e.setId(2);
			empserv.ajouterEmploye(e);
			empserv.affecterEmployeADepartement(e.getId(),1);
			assertEquals(time.validerTimesheet(1,2,date,date2,1),1); 
		}
		//test find all missions by employe
		 @Test
		 public void Testcase_5()
		 {
			 //System.out.println(time.findAllMissionByEmployeJPQL(2).size());
			 Assertions.assertThat(time.findAllMissionByEmployeJPQL(2)).isNotEmpty();
		 }
		 
		 //test get all employes by mission
		 @Test
		 public void Testcase_6()
		 {
			 Assertions.assertThat(time.getAllEmployeByMission(1)).isNotEmpty();
			 empserv.desaffecterEmployeDuDepartement(2,1);
			 //empserv.deleteEmployeById(2);
		 }
	
	
	
	
	
	
	
	
	
}
