package ua.org.smit.amvsampler.service.exportsamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.util.FilesUtil;

public class ExportSamplesService {

    private static final Logger log = LogManager.getLogger(ExportSamplesService.class);

    public void export(List<Sample> samplesExport, File exportFolder) {
        samplesExport.stream().forEach((sample) -> {
            export(sample, exportFolder);
        });
    }

    public void export(Sample sample, File exportFolder) {
        log.info("Export sample: " + sample + " to folder: " + exportFolder);
        File sampleFolder = new File(
                exportFolder + File.separator
                + sample.getTitle() + "_ss_" + sample.getSs());
        sampleFolder.mkdir();

        File mp4 = new File(sample.getTitle() + "_ss_" + sample.getSs() + ".mp4");
        FilesUtil.copy(sample.getMp4(), new File(sampleFolder + File.separator + mp4));

        File gif = new File(sample.getTitle() + "_ss_" + sample.getSs() + ".gif");
        FilesUtil.copy(sample.getGif(), new File(sampleFolder + File.separator + gif));
    }

    public ArrayList<SampleExport> getExportSamples(File exportFolder) {
        ArrayList<SampleExport> samples = new ArrayList();
        FilesUtil.getAllFoldersNotRecursive(exportFolder).stream().forEach((sampleFolder) -> {
            try {
                SampleExport sample = findSample(sampleFolder);
                log.info("sample found: " + sample);
                samples.add(sample);
            } catch (FileNotFoundException ex) {
                log.error("Cant find samples in folder: '" + sampleFolder + "' " + ex);
            }
        });
        return samples;
    }

    private SampleExport findSample(File sampleFolder) throws FileNotFoundException {
        ArrayList<File> files = FilesUtil.getFiles(sampleFolder);
        SampleExport sampleExport = new SampleExport();
        sampleExport.setSampleName(sampleFolder.getName());
        sampleExport.setMp4(FilesUtil.findByExtension(files, "mp4"));
        sampleExport.setGif(FilesUtil.findByExtension(files, "gif"));
        return sampleExport;
    }
}
