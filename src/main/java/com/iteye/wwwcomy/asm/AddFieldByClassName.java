package com.iteye.wwwcomy.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * 去除对class的引用，这样在处理class之前，class并没有被加载，如果在这个处理后再加载，就可以直接使用改过的class
 * 
 * @author xingnan.liu
 * @version $Revision$
 */
public class AddFieldByClassName {

    private String className = null;
    private ClassReader cr = null;
    private ClassWriter cw = null;
    private ClassAdapter ca = null;
    private File classFile = null;

    private final static String CLASS_FILE_SUFFIX = ".class";

    public AddFieldByClassName(String className) {
        this.className = className;
    }

    /**
     * 添加一个 public 的类成员
     * 
     * @param fieldName
     *            类成员名
     * @param fieldDesc
     *            类成员类型描述
     */
    public void addPublicField(String fieldName, String fieldDesc) {
        if (cr == null) {
            try {
                cr = new ClassReader(className);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        }
        if (ca == null) {
            ca = new AddFieldAdapter(cw, Opcodes.ACC_PUBLIC, fieldName, fieldDesc);
        } else {
            ca = new AddFieldAdapter(ca, Opcodes.ACC_PUBLIC, fieldName, fieldDesc);
        }
    }

    /**
     * 将字节码写入类的 .class 文件
     * 
     */
    public void writeByteCode() {
        cr.accept(ca, ClassReader.SKIP_DEBUG);
        byte[] bys = cw.toByteArray();
        OutputStream os = null;
        try {
            os = new FileOutputStream(getFile());
            os.write(bys);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得类文件的 File 对象
     * 
     * @return
     */
    private File getFile() {
        if (classFile == null) {
            StringBuffer sb = new StringBuffer();
            sb.append(AddFieldByClassName.class.getResource("/")).append(className.replace(".", File.separator))
                    .append(CLASS_FILE_SUFFIX);
            classFile = new File(sb.substring(6));
        }
        return classFile;
    }
}
