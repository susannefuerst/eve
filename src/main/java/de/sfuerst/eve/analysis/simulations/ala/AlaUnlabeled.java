package de.sfuerst.eve.analysis.simulations.ala;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import de.kempalab.msdps.Fragment;
import de.kempalab.msdps.FragmentList;
import de.kempalab.msdps.FragmentsDatabase;
import de.kempalab.msdps.MassSpectrum;
import de.kempalab.msdps.constants.FragmentKey;
import de.kempalab.msdps.constants.IntensityType;
import de.kempalab.msdps.data.IncorporationRate;
import de.kempalab.msdps.exception.FragmentNotFoundException;
import de.kempalab.msdps.exception.TypeMismatchException;
import de.kempalab.msdps.log.MyLogger;
import de.kempalab.msdps.simulation.IsotopePatternSimulator;
import de.kempalab.msdps.simulation.IsotopePatternSimulatorRequest;
import de.kempalab.msdps.simulation.IsotopePatternSimulatorResponse;
import de.kempalab.msdps.visualisation.MSLineChartApplicationWindow;

public class AlaUnlabeled {

    public static final MyLogger LOGGER = MyLogger.getLogger(AlaUnlabeled.class);

    public static void main(String[] args) throws TypeMismatchException, IOException, FragmentNotFoundException {
	IsotopePatternSimulatorRequest simulatorRequest = new IsotopePatternSimulatorRequest();
	Fragment fragment1 = FragmentsDatabase.getFragment(FragmentKey.ALA_116);
	Fragment fragment2 = FragmentsDatabase.getFragment(FragmentKey.ALA_188);
	Fragment fragment3 = FragmentsDatabase.getFragment(FragmentKey.ALA_190);
	Fragment fragment4 = FragmentsDatabase.getFragment(FragmentKey.ALA_262);
	simulatorRequest.setFragments(new FragmentList(fragment1, fragment2, fragment3, fragment4));
	simulatorRequest.setIncorporationRate(new IncorporationRate(0.0));
	simulatorRequest.setMinimalIntensity(0.05);
	simulatorRequest.setAnalyzeMassShifts(false);
	simulatorRequest.setTotalNumberOfFragments(10000.0);
	simulatorRequest.setRoundedMassPrecision(4);
	simulatorRequest.setTargetIntensityType(IntensityType.RELATIVE);
	simulatorRequest.setCharge(1);
	IsotopePatternSimulatorResponse response = IsotopePatternSimulator.simulate(simulatorRequest);
	MassSpectrum spectrum1 = response.getMsDatabaseList().get(0).getMixedSpectrum();
	MassSpectrum spectrum2 = response.getMsDatabaseList().get(1).getMixedSpectrum();
	MassSpectrum spectrum3 = response.getMsDatabaseList().get(2).getMixedSpectrum();
	MassSpectrum spectrum4 = response.getMsDatabaseList().get(3).getMixedSpectrum();

	MassSpectrum spectrum = spectrum1.merge(spectrum2);
	spectrum = spectrum.merge(spectrum3);
	spectrum = spectrum.merge(spectrum4);
//    MassSpectrum continuous = spectrum.simulateContinuousHighRes(60000,
//            100, false);
    MassSpectrum continuous = spectrum3.simulateContinuousHighRes(60000,
            100, false);

//    continuous.toDataTable().writeToCsv("N/A", true,
//            PathConstants.FILE_OUTPUT_FOLDER.toAbsolutePath(fragment188
//                    .getFragmentKey().getMetaboliteKey().getAbbreviation()
//                    + "\\ala_60k"));

     MSLineChartApplicationWindow demo = new MSLineChartApplicationWindow(
     "LA-UNLABELED", "ALA-UNLABELED", "test", continuous,
     true);
     demo.pack();
     demo.setSize(1300, 750);
     RefineryUtilities.centerFrameOnScreen(demo);
     demo.setVisible(true);
    }


}
