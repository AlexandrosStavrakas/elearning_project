/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omada5Test.ElearningProjectTest.testService;

import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Before;

import omada5.ElearningProject.persistence.*;

/**
 *
 * @author thegr
 */
public abstract class ElearningProjectServiceTest 
{
    Initializer dataHelper;

    /**
     *
     */
    protected EntityManager em;

    /**
     *
     */
    public ElearningProjectServiceTest() 
    {
        super();
    }

    /**
     *
     */
    protected void beforeDatabasePreparation(){}

    /**
     *
     */
    protected void afterDatabasePreparation(){}

    /**
     *
     */
    @Before
    public final void setUp() 
    {
        beforeDatabasePreparation();
        dataHelper = new Initializer();
        dataHelper.prepareData();
        em = JPAUtil.getCurrentEntityManager();
        afterDatabasePreparation();
    }

    /**
     *
     */
    protected void afterTearDown(){}

    /**
     *
     */
    @After
    public final void tearDown() 
    {
        em.close();
        afterTearDown();
    }

}
