package ch.heigvd.res.lab01.impl.explorers;

import ch.heigvd.res.lab01.interfaces.IFileExplorer;
import ch.heigvd.res.lab01.interfaces.IFileVisitor;
import java.io.File;

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
      /*
      File[] files = rootDirectory.listFiles();
      for(int i = 0; i < files.length; i++){
          if(files[i].isDirectory()){
              explore(files[i], vistor);
          }else{
              vistor.visit(files[i]);
          }
      }
      */
      vistor.visit(rootDirectory);
      if(rootDirectory.isDirectory()){
          File[] files = rootDirectory.listFiles();
          for(int i = 0; i < files.length; i++){
              if(files[i].isFile())
                explore(files[i], vistor); 
          }
          for(int i = 0; i < files.length; i++){
              if(files[i].isDirectory())
                explore(files[i], vistor); 
          }
      }
      
      
      
  }

}
