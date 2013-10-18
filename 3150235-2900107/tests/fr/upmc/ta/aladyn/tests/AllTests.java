package fr.upmc.ta.aladyn.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BackupManagerTest.class, MethodeCouranteManagerTest.class, MyPointTest.class, ScenariiTests.class })
public class AllTests {}
