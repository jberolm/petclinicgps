package org.springframework.samples.petclinic.vet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
public class VetRepositoryTest {
	
	@Autowired
	private VetRepository vets;
	
	private Vet vet;
	
	@Before
	public void init() {
		//Collection <Vet> cVets = 
		
		if (this.vet==null) {
			vet = new Vet ();
			vet.setFirstName("Javier");
			vet.setLastName("Berrocal");
			vet.setHomeVisits(false);
			
			Collection <Specialty> vetSpec = this.vets.findSpecialties();
			
			if (!vetSpec.isEmpty()) {				
				for (Specialty spec : vetSpec) {
					vet.addSpecialty(spec);
				}				
			}			
			
			this.vets.save(vet);
		}
	}
	
	
	@Test
	@Transactional
	public void testSave() {
		Vet vetSave = new Vet ();
		vetSave.setFirstName("Juan Manuel");
		vetSave.setLastName("Murillo");
		vetSave.setHomeVisits(true);
		
		assertNull(vetSave.getId());
		
		this.vets.save(vetSave);
		
		assertNotNull(vetSave.getId());
	}
	
	@Test
	public void testFindById() {
		Vet vetFindById = this.vets.findById(vet.getId());
		
		assertNotNull(vetFindById.getFirstName());
		assertEquals(vetFindById.getFirstName(), vet.getFirstName());	
		
		assertNotNull(vetFindById.getLastName());
		assertEquals(vetFindById.getLastName(), vet.getLastName());	
		
		assertEquals(vetFindById.getHomeVisits(), vet.getHomeVisits());
		
		assertEquals(vetFindById.getSpecialties().size(), vet.getSpecialties().size());
		
		if (vetFindById.getSpecialties().size()>0) {
			assertArrayEquals(vetFindById.getSpecialties().toArray(), vet.getSpecialties().toArray());
		}
		
	}
	
	@Test
	public void testFindByIdNotEqual() {
		
		Vet vetSave = new Vet ();
		vetSave.setFirstName("Juan Manuel");
		vetSave.setLastName("Murillo");
		vetSave.setHomeVisits(true);
		
		assertNull(vetSave.getId());
		
		this.vets.save(vetSave);
		
		assertNotNull(vetSave.getId());
		
		
		Vet vetFindById = this.vets.findById(vet.getId());
		
		assertNotNull(vetFindById.getFirstName());
		assertNotEquals(vetFindById.getFirstName(), vetSave.getFirstName());	
		
		assertNotNull(vetFindById.getLastName());
		assertNotEquals(vetFindById.getLastName(), vetSave.getLastName());	
		
		assertNotEquals(vetFindById.getHomeVisits(), vetSave.getHomeVisits());
		
		assertNotEquals(vetFindById.getSpecialties().size(), vetSave.getSpecialties().size());		
	}
	
	@Test
	public void testUpdate() {
		this.vet.setFirstName("Juan Manuel");
		this.vet.setLastName("Murillo");
				
		this.vets.save(this.vet);
		
		Vet vetUpdate = this.vets.findById(this.vet.getId());
		assertEquals("Juan Manuel", vetUpdate.getFirstName());
		assertEquals("Murillo", vetUpdate.getLastName());		
	}
	
	@Test
	public void testFindAll() {
		Collection <Vet> vetsAll = this.vets.findAll();
		
		assertTrue(vetsAll.size()>=1);
		
		for (Vet vet : vetsAll) {
			if (vet.getId() == this.vet.getId()) {
				assertNotNull(vet.getFirstName());
				assertEquals(vet.getFirstName(), vet.getFirstName());	
				
				assertNotNull(vet.getLastName());
				assertEquals(vet.getLastName(), vet.getLastName());	
				
				assertEquals(vet.getHomeVisits(), vet.getHomeVisits());
				
				assertEquals(vet.getSpecialties().size(), vet.getSpecialties().size());
			}
		}
		
	}
	
	@After
	public void finish() {
		this.vet=null;
	}
	

}
