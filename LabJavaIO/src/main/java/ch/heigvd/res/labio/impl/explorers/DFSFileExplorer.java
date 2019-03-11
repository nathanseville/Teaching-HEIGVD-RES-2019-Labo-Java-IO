package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import org.apache.commons.io.filefilter.FileFileFilter;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    if(rootDirectory == null)
      return;

    vistor.visit(rootDirectory);

    if(rootDirectory.isDirectory()) {
      /* -- and then moves into the subdirectories. -- */
      File[] directories = rootDirectory.listFiles(File::isDirectory);
      Arrays.sort(directories);

      for (File directory : directories)
        explore(directory, vistor);

      // switched two parts <> for test purposes

      /* -- it visits all files in the directory -- */
      File[] files = rootDirectory.listFiles(File::isFile);
      Arrays.sort(files);

      for(File file : files)
        vistor.visit(file); // <-> explore(file, visitor);
    }
  }

}
