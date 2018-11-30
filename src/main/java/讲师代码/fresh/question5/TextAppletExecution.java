package 讲师代码.fresh.question5;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 *
 * @author Mingxin Wang
 */
abstract class TextAppletExecution implements AppletExecution {
    // 通用文本处理程序解析过程
    @Override
    public String execute(String input, Iterator<String> options) throws CommandExecutionException {
        boolean readFromInput = true;
        while (options.hasNext()) {
            String option = options.next();
            // 对于每个option，有五种情况：
            //   1. option被指定为“-”，则从标准输入读取数据
            //   2. option由“--”开头，则是一个选项开关
            //   3. option不是“-”且由“-”开头，则是一个或多个选项开关
            //   4. 将option作为一个文件名，则读取并处理文件，追加至输出
            //   5. option是一个内部解析的选项（通用情况）
            if (option.equals("-")) {
                addData(input);
                readFromInput = false;
                input = "";
            } else if (option.startsWith("--")) {
                setFlag(option.substring(2));
            } else if (option.startsWith("-")) {
                for (char flag : option.substring(1).toCharArray()) {
                    setFlag(flag);
                }
            } else if (acceptsData()) {
                try {
                    addData(Files.asCharSource(new File(option), Charset.defaultCharset()).read());
                    readFromInput = false;
                } catch (IOException e) {
                    throw new CommandExecutionException(e);
                }
            } else {
                addParam(option);
            }
        }

        // 若无输入文件且程序接收输入内容，则将标准输入作为输入内容
        if (readFromInput && acceptsData()) {
            addData(input);
        }
        return getResult();
    }

    // 设置 --potion 选项
    protected void setFlag(String flag) throws CommandExecutionException {
        throw new CommandExecutionException("无法识别的选项", flag);
    }

    // 设置 -xyz 选项
    protected void setFlag(char flag) throws CommandExecutionException {
        throw new CommandExecutionException("无法识别的选项", "" + flag);
    }

    // 是否可以接收数据
    protected boolean acceptsData() {
        return true;
    }

    // 增加通用参数
    protected void addParam(String param) {
    }

    // 输入数据
    protected abstract void addData(String input) throws CommandExecutionException;

    // 获取结果
    protected abstract String getResult() throws CommandExecutionException;
}
