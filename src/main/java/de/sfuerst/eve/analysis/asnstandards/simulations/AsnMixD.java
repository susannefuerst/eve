package de.sfuerst.eve.analysis.asnstandards.simulations;

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;

import de.kempalab.msdps.Fragment;
import de.kempalab.msdps.FragmentList;
import de.kempalab.msdps.FragmentsDatabase;
import de.kempalab.msdps.MassSpectrum;
import de.kempalab.msdps.constants.Element;
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

public class AsnMixD {

    public static final MyLogger LOGGER = MyLogger.getLogger(AsnMixC.class);

    public static void main(String[] args)
	    throws TypeMismatchException, IOException, FragmentNotFoundException {
	//	fragment 188 spectrum:
	IsotopePatternSimulatorRequest simulatorRequest188 = new IsotopePatternSimulatorRequest();
	Fragment fragment188 = FragmentsDatabase.getFragment(FragmentKey.ASN_188);
	simulatorRequest188.setFragments(new FragmentList(fragment188));
	simulatorRequest188.setTracer1(Element.C);
	simulatorRequest188.setTracer2(Element.N);
	simulatorRequest188.setTracer1Inc(new IncorporationRate(0.0));
	simulatorRequest188.setTracer2Inc(new IncorporationRate(0.333));
	simulatorRequest188.setTracerAllInc(new IncorporationRate(0.333));
	simulatorRequest188.setMinimalIntensity(0.003);
	simulatorRequest188.setAnalyzeMassShifts(false);
	simulatorRequest188.setTotalNumberOfFragments(10000.0);
	simulatorRequest188.setRoundedMassPrecision(4);
	simulatorRequest188.setTargetIntensityType(IntensityType.MID);
	simulatorRequest188.setCharge(1);
	IsotopePatternSimulatorResponse response188 = IsotopePatternSimulator
		.simulateIndependentTracerIncorporation(simulatorRequest188);
	MassSpectrum spectrum188 = response188.getMsDatabaseList().get(0).getMixedSpectrum();

	//	fragment 243 + 419 spectrum:
	//	the 15N2-13C4 part
	IsotopePatternSimulatorRequest simulatorRequest_1 = new IsotopePatternSimulatorRequest();
	Fragment fragment243_1 = FragmentsDatabase.getFragment(FragmentKey.ASN_243);
	Fragment fragment419_1 = FragmentsDatabase.getFragment(FragmentKey.ASN_419);
	simulatorRequest_1.setFragments(new FragmentList(fragment243_1, fragment419_1));
	simulatorRequest_1.setTracer1(Element.C);
	simulatorRequest_1.setTracer2(Element.N);
	simulatorRequest_1.setTracer1Inc(new IncorporationRate(0.0));
	simulatorRequest_1.setTracer2Inc(new IncorporationRate(0.0));
	simulatorRequest_1.setTracerAllInc(new IncorporationRate(1.0));
	simulatorRequest_1.setMinimalIntensity(0.003);
	simulatorRequest_1.setAnalyzeMassShifts(false);
	simulatorRequest_1.setTotalNumberOfFragments(10000.0);
	simulatorRequest_1.setRoundedMassPrecision(4);
	simulatorRequest_1.setTargetIntensityType(IntensityType.MID);
	simulatorRequest_1.setCharge(1);
	IsotopePatternSimulatorResponse response_1 = IsotopePatternSimulator
		.simulateIndependentTracerIncorporation(simulatorRequest_1);

	//	the 15N part
	IsotopePatternSimulatorRequest simulatorRequest_2 = new IsotopePatternSimulatorRequest();
	Fragment fragment243_2 = FragmentsDatabase.getFragment(FragmentKey.ASN_243);
	fragment243_2.changeCapacity("N");
	Fragment fragment419_2 = FragmentsDatabase.getFragment(FragmentKey.ASN_419);
	fragment419_2.changeCapacity("N");
	simulatorRequest_2.setFragments(new FragmentList(fragment243_2, fragment419_2));
	simulatorRequest_2.setTracer1(Element.C);
	simulatorRequest_2.setTracer2(Element.N);
	simulatorRequest_2.setTracer1Inc(new IncorporationRate(0.0));
	simulatorRequest_2.setTracer2Inc(new IncorporationRate(1.0));
	simulatorRequest_2.setTracerAllInc(new IncorporationRate(0.0));
	simulatorRequest_2.setMinimalIntensity(0.003);
	simulatorRequest_2.setAnalyzeMassShifts(false);
	simulatorRequest_2.setTotalNumberOfFragments(10000.0);
	simulatorRequest_2.setRoundedMassPrecision(4);
	simulatorRequest_2.setTargetIntensityType(IntensityType.MID);
	simulatorRequest_2.setCharge(1);
	IsotopePatternSimulatorResponse response_2 = IsotopePatternSimulator
		.simulateIndependentTracerIncorporation(simulatorRequest_2);

	//	the unlabeled part
	IsotopePatternSimulatorRequest simulatorRequest_3 = new IsotopePatternSimulatorRequest();
	Fragment fragment243_3 = FragmentsDatabase.getFragment(FragmentKey.ASN_243);
	Fragment fragment419_3 = FragmentsDatabase.getFragment(FragmentKey.ASN_419);
	simulatorRequest_3.setFragments(new FragmentList(fragment243_3, fragment419_3));
	simulatorRequest_3.setIncorporationRate(new IncorporationRate(0.0));
	simulatorRequest_3.setMinimalIntensity(0.003);
	simulatorRequest_3.setAnalyzeMassShifts(false);
	simulatorRequest_3.setTotalNumberOfFragments(10000.0);
	simulatorRequest_3.setRoundedMassPrecision(4);
	simulatorRequest_3.setTargetIntensityType(IntensityType.MID);
	simulatorRequest_3.setCharge(1);
	IsotopePatternSimulatorResponse response_3 = IsotopePatternSimulator.simulate(simulatorRequest_3);

	//	merge spectra:
	MassSpectrum spectrum243_1 = response_1.getSpectrum(0);
	MassSpectrum spectrum243_2 = response_2.getSpectrum(0);
	MassSpectrum spectrum243labeled = spectrum243_1.merge(spectrum243_2);
	spectrum243labeled = IsotopePatternSimulator.prepareSpectrum(spectrum243labeled, 4, 4, 0.003, IntensityType.MID).scale(0.666);
	MassSpectrum spectrum243unlabeled = response_3.getSpectrum(0).scale(0.333);
	MassSpectrum spectrum243 = spectrum243labeled.merge(spectrum243unlabeled);

	MassSpectrum spectrum419_1 = response_1.getSpectrum(1);
	MassSpectrum spectrum419_2 = response_2.getSpectrum(1);
	MassSpectrum spectrum419labeled = spectrum419_1.merge(spectrum419_2);
	spectrum419labeled = IsotopePatternSimulator.prepareSpectrum(spectrum419labeled, 4, 4, 0.003, IntensityType.MID).scale(0.666);
	MassSpectrum spectrum419unlabeled = response_3.getSpectrum(1).scale(0.333);
	MassSpectrum spectrum419 = spectrum419labeled.merge(spectrum419unlabeled);
	
	MassSpectrum spectrum = spectrum188.merge(spectrum243);
	spectrum = spectrum.merge(spectrum419);
	spectrum = IsotopePatternSimulator.prepareSpectrum(spectrum, 4, 4, 0.1, IntensityType.RELATIVE);

	//	simulate high res:
        MassSpectrum continuous = spectrum.simulateContinuousHighRes(120000,
                100, false);
	MSLineChartApplicationWindow demo = new MSLineChartApplicationWindow("ASN-MIX-D", "ASN-MIX-D", "15N + 15N2,13C4 + unlabeled, 1:1:1", continuous);
	demo.pack();
	RefineryUtilities.centerFrameOnScreen(demo);
	demo.setVisible(true);
    }


}
