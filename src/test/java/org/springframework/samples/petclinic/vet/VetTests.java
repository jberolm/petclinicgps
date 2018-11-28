/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class VetTests {
	
	public static Vet vet;
	public static Vet vet2;
	
	@BeforeClass
	//This method is called before executing this suite
	public static void initClass() {
		vet = new Vet();
		vet.setFirstName("Juan Manuel");
		vet.setLastName("Murillo");
	}
	
	@Before
	//This test is executed before each test created in this suite
	public void init() {
		vet.setHomeVisits(false);
	}
	
	@Test
	public void testVet() {
		assertNotNull(vet);				
	}
	
	@Test
	public void testVet2() {
		assertNull(vet2);				
	}
	
	@Test
	public void testFirstName() {
		assertNotNull(vet.getFirstName());
		assertNotEquals(vet.getFirstName(), "JuanMa");
		assertEquals(vet.getFirstName(), "Juan Manuel");		
	}
	
	@Test
	public void testLastName() {
		assertNotNull(vet.getLastName());
		assertEquals(vet.getLastName(), "Murillo");
		assertNotEquals(vet.getLastName(), "Rodríguez");		
	}
	
	@Test
	public void testHomeVisits() {
		assertNotNull(vet.getHomeVisits());
		assertFalse(vet.getHomeVisits());
	}
	
    @Test
    public void testSerialization() {
    	Vet vet3 = new  Vet();
        vet3.setFirstName("Zaphod");
        vet3.setLastName("Beeblebrox");
        vet3.setId(123);
        Vet other = (Vet) SerializationUtils
                .deserialize(SerializationUtils.serialize(vet3));
        assertThat(other.getFirstName()).isEqualTo(vet3.getFirstName());
        assertThat(other.getLastName()).isEqualTo(vet3.getLastName());
        assertThat(other.getId()).isEqualTo(vet3.getId());
    }
    
	@Ignore
	//This test is not executed
	public void ignore() {
		vet.setFirstName("JuanMa");
	}
    
    @After
    //This test is executed after each test created in this suite
    public void finish() {
    	vet.setHomeVisits(true);
    }
    
    @AfterClass
    //This method is executed after all the tests included in this suite are completed.
    public static void finishClass() {
    	vet = null;
    }
    

}
