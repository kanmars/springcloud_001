package cn.com.xcommon.commons.javascript;

import javax.script.*;

/**
 * Created by baolong on 2017/1/12.
 */
public class JavascriptTools {
    static ScriptEngineManager sem = new ScriptEngineManager();
    static ScriptEngine se = sem.getEngineByName("javascript");

    /**
     * 编译并执行
     * @param script
     * @throws ScriptException
     */
    public static void eval(String script) throws ScriptException {
        eval(script, true);//默认编译执行
    }

    /**
     * 编译并执行
     * @param script
     * @throws ScriptException
     */
    public static void eval(String script,boolean isCompile) throws ScriptException {
        if(isCompile){
            Compilable compEngine = (Compilable) se;
            CompiledScript compiledScript = compEngine.compile(script);
            compiledScript.eval();
        }else{
            se.eval(script);
        }
    }

    /**
     * 执行函数
     * @param functionName
     * @param args
     * @return
     * @throws ScriptException
     * @throws NoSuchMethodException
     */
    public static Object invokeFunction(String functionName,Object... args) throws ScriptException, NoSuchMethodException {
        Invocable invocableEngine = (Invocable) se;
        Object result = invocableEngine.invokeFunction(functionName,args);
        return result;
    }

    public static void put(String key,Object value) throws ScriptException, NoSuchMethodException {
        se.put(key,value);
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        eval("function ccc(a,b){println('cccc')}");
        invokeFunction("ccc");
        demo001();
    }

    private static void demo001(){
        try {
            {
                //1、调用直接JAVASCRIPT语句
                se.eval("println('111');");
                String tmpstr = "test string";
                se.eval(("println('" + tmpstr + "');"));
            }
            {
                //2、调用无参数方法JAVASCRIPT函数
                se.eval("function sayHello() {"
                        + " print('Hello '+strname+'!');"
                        + " return 'my name is '+strname;"
                        + "}");
                Invocable invocableEngine = (Invocable) se;
                se.put("strname", "testname");
                String callbackvalue = (String) invocableEngine.invokeFunction("sayHello");
                System.out.println(callbackvalue);
            }

            {
                //3、调用有参数JAVASCRIPT函数
                se.eval("function sayHello2(strname2) {"
                        + " print('Hello '+strname+'!');"
                        + " return 'my name is '+strname2;"
                        + "}");
                Invocable invocableEngine = (Invocable) se;
                String callbackvalue = (String) invocableEngine.invokeFunction("sayHello2", "testname2");
                System.out.println(callbackvalue);
            }


            {
                //4、将javascript函数作为接口调用
                se.eval("function max_num(a,b){return (a>b)?a:b;}");
                Invocable invoke = (Invocable) se;
                JSLib jslib = invoke.getInterface(JSLib.class);
                int maxNum = jslib.max_num(4, 6);
                System.out.println(maxNum);
            }

            {
                //5、直接调用java方法
                String script = "var list = java.util.ArrayList();list.add(\"kafka0102\");print(list.get(0));";
                se.eval(script);
                se.eval("importPackage(javax.swing);" + "var optionPane = JOptionPane.showMessageDialog(null, 'Hello, world!');");
            }

            {
                //6、编译执行某方法
                Compilable compEngine = (Compilable) se;
                CompiledScript compiledScript = compEngine.compile("function max_num(a,b){return (a>b)?a:b;}");
                compiledScript.eval();
                Invocable invoke = (Invocable) se;
                Object maxNum = invoke.invokeFunction("max_num", 4, 6);
                System.out.println(maxNum);
            }




        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public interface JSLib {
        public int max_num(int a, int b);
    }

}
