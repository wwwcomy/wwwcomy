package test.test_svn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
/**  
 * SVNManager SVN 管理器  
 * @author <a href="xiangxji@gmail.com">xiangxji</a>  
 * @since 2010-03-27  
 */  
public class SVNManager{  
      
    private String url = 
//    		"http://zhouxw-think:8090/svn/suddenzhou/";
    		"file:///E:/svn_db/suddenzhou/"; 
//    		"svn://localhost/";  
    private String username = "test";  
    private String password = "test";  
    private SVNRepository repository;  
  
    /**  
     * 初始化操作  
     * @throws Exception  
     */  
    public void initialize() throws Exception {  
        FSRepositoryFactory.setup();  
        DAVRepositoryFactory.setup();  
        SVNRepositoryFactoryImpl.setup();  
        repository = SVNRepositoryFactoryImpl.create(SVNURL.parseURIEncoded(this.url));  
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(this.username, this.password);  
        repository.setAuthenticationManager(authManager);  
    }  
      
    /**  
     * 从SVN服务器获取文件  
     * @param filePath 相对于仓库根目录的路径  
     * @param outputStream 要输出的目标流，可以是文件流 FileOutputStream  
     * @param version 要checkout的版本号  
     * @return 返回checkout文件的版本号  
     * @throws Exception 可以自定义Exception  
     */  
    public long getFileFromSVN(String filePath, OutputStream outputStream,  
            long version) throws Exception {  
        SVNNodeKind node = null;  
        try {  
            node = repository.checkPath(filePath, version);  
        } catch (SVNException e) {  
            throw new Exception("SVN检测不到该文件:" + filePath, e);  
        }  
        if (node != SVNNodeKind.FILE) {  
            throw new Exception(node.toString() + "不是文件");  
        }  
        //SVNProperties properties = new SVNProperties();  
        SVNProperties properties = new SVNProperties();
        try {  
            repository.getFile(filePath, version, properties, outputStream);  
        } catch (SVNException e) {  
            throw new Exception("获取SVN服务器中的" + filePath + "文件失败", e);  
        }  
        //return Long.parseLong(properties.getStringValue("svn:entry:revision"));  
        return Long.parseLong(properties.getStringValue("svn:entry:revision"));  
    }  
    /**  
     * 获取目录下的所有文件和子目录  
     * @param res 包含目录参数的资源对象.参加{@link Resource#getPath()}  
     * @return 资源列表  
     * @throws Exception  
     */  
    @SuppressWarnings("unchecked")  
    public List<Resource> getChildren(Resource res) throws Exception {  
        String path = res.getPath();  
        Collection<SVNDirEntry> entries;  
        try {  
            entries = repository.getDir(path, -1, null, (Collection<?>) null);  
        } catch (SVNException e) {  
            throw new Exception("获得" + path + "下级目录失败", e);  
        }  
        List<Resource> result = new ArrayList<Resource>();  
        for (SVNDirEntry entry : entries) {  
            if (containsSpecialFile(entry)) {  
                Resource resource = new Resource();  
                resource.setName(entry.getName());  
                resource.setPath(entry.getURL().getPath());  
                resource.setFile(entry.getKind() == SVNNodeKind.FILE);  
                resource.setSVNVersion(entry.getRevision());
                result.add(resource);  
            }  
        }  
        return result;  
    }  
    /**  
     * 判断文件是否存在  
     * @param entry 要判断的节点.参加{@link SVNDirEntry}  
     * @return   
     * @throws Exception  
     */  
    @SuppressWarnings("unchecked")  
    private boolean containsSpecialFile(SVNDirEntry entry)  
    throws Exception {  
        if (entry.getKind() == SVNNodeKind.FILE) {  
            return true;  
        } else if (entry.getKind() == SVNNodeKind.DIR) {  
            Collection<SVNDirEntry> entries;  
            String path = entry.getURL().getPath();  
            try {  
                entries = repository.getDir(path, -1, null, (Collection<?>) null);  
            } catch (SVNException e) {  
                throw new Exception("获得" + path + "下级目录失败", e);  
            }  
            for (SVNDirEntry unit : entries) {  
                if (containsSpecialFile(unit)) {  
                    return true;  
                }  
            }  
            return false;  
        }  
        return false;  
    }  
      
    @Test  
    public void testGetFile() {   
        OutputStream outputStream = null;  
        try {  
            initialize();  
            String outFileName = "E:/svn_workspace/log4j-1.2.8.jar";  
            outputStream = new FileOutputStream(outFileName);  
            System.out.println(getFileFromSVN("/project/test/log4j-1.2.8.jar",outputStream,7L));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {
        	if (outputStream!=null)
	        	try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }  
      
    @Test  
    public void testGetDir() {  
        OutputStream outputStream = null;
        try {
        	outputStream = new FileOutputStream("E:/svn_workspace/temp");   
        	
            initialize();  
            Resource res = new Resource(); 
            String sPath = "/project/test/ws/";
            res.setPath(sPath);  
            List<Resource> rs = getChildren(res);  
            for(Resource r : rs) {
                System.out.println((r.getFile()?"file:":"directory:") + r.getPath());  
                if (r.getFile()) {
	                System.out.println(getFileFromSVN(sPath+r.getName(),outputStream,r.getSVNVersion()));  
                }  
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {
        	if (outputStream!=null)
	        	try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }  
    
    
}  