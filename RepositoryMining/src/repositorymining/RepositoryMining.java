/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositorymining;

/**
 *
 * @author ruudandriessen
 */
public class RepositoryMining {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         XMLManager.load(new PageProcessor() {
            @Override
            public void process(Post page) {
                
                System.out.println(page);
                
           }
        }) ;
    }
}
