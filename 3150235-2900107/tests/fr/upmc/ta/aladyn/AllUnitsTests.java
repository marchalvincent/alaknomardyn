package fr.upmc.ta.aladyn;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fr.upmc.ta.aladyn.backup.BackupManagerTest;
import fr.upmc.ta.aladyn.backup.MethodeCouranteManagerTest;
import fr.upmc.ta.aladyn.backup.MyPointTest;

/**
 * Lance tous les tests unitaires.
 * 
 * @author Michel Knoertzer & Vincent Marchal
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ BackupManagerTest.class, MethodeCouranteManagerTest.class, MyPointTest.class })
public class AllUnitsTests {
}
