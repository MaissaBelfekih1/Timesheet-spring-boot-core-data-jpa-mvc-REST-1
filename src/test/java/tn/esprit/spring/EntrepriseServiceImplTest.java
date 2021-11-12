package tn.esprit.spring;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	@Autowired
	IEntrepriseService se;
	
	Entreprise e1 = new Entreprise("mouna","mariem");
	Entreprise e2 = new Entreprise("mouna1","mariem2");
	Departement d1 = new Departement("info");
	Entreprise e = new Entreprise();
	
	

	@Test
    public void Testcase_1(){

        assertEquals(true,se.ajouterEntreprise(e2));

    }
	@Test
	public void Testcase_2()
	{
	assertEquals(true,se.ajouterDepartement(d1));
	}
	
	
	 @Test
	    public void Testcase_3()
	    {
	        assertEquals(1 ,se.affecterDepartementAEntreprise(1, 1));
	    }
	 
	 @Test
		public void Testcase_4(){
			ArrayList<String> d= new ArrayList<>();
			d= (ArrayList<String>) se.getAllDepartementsNamesByEntreprise(1);
			assertEquals(d,se.getAllDepartementsNamesByEntreprise(1));
		}
	 
	@Test
    public void Testcase_5()
    {
        assertEquals(1,se.getEntrepriseById(1).getId());


    }
	
	
	 
	 @Test
	    public void Testcase_6(){
		    se.ajouterEntreprise(e2);
	        assertEquals(true,se.deleteEntrepriseById(2));
	    }
		@Test
		public void Testcase_7(){ 
			se.ajouterDepartement(d1);
			assertEquals(true, se.deleteDepartementById(2));
		}
}
