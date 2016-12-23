package cn.com.xcommon.frame.logger;

/**
 * 使用char数组实现的ByteLogUtil，性能较好，实际使用中应使用该类
 * 
 * @author baolong 20140121
 *
 */
public class ByteLogUtil {
	/**
	 * 默认的打印字节数量
	 */
	public static final int DEFAULTBYTELENGTH = 16;// 需要是偶数，最好是6的倍数
	public static final String PREFIX = "    "; // 日志的前缀
	public static final String SUFFIX = "    "; // 文字信息的前缀
	public static final int indexlength = 10; // 序号区域的长度
	public static final int bitmaplength = DEFAULTBYTELENGTH * 3 + 1;// 位图区域的长度
	public static final int messagelength = (int) (DEFAULTBYTELENGTH * 1.5 + SUFFIX.length());// 内容区域的长度

	/**
	 * 传入message的byte日志，输出message的字符串日志，每行默认16字节 最终日志的长度应为16*6=96字节
	 * 
	 * @param message
	 * @return
	 */
	public static char[] getByteLogMessage(byte[] message) {
		return getByteLogMessage(message, DEFAULTBYTELENGTH);
	}

	/**
	 * 传入message的byte日志，输出message的字符串日志，每行默认length长度的字节 最终日志的长度应为length*6
	 * 
	 * @param message
	 * @param length
	 * @return
	 */
	public static char[] getByteLogMessage(byte[] message, int length) {

		char[] resultChars = null;
		try {
			/** 初始化resultChars */
			// 计算resultChars的长度
			// length = 2 \r\n
			// + PREFIX.length() + 1 + indexlength + 1 + (3*DEFAULTBYTELENGTH +1) + 1 + (messagelength +1) +2
			// *(n+4) 1+indexlength+1+bitmaplength+1+messagelength+1
			// +2 \r\n
			int num_row = message.length / DEFAULTBYTELENGTH + (message.length % DEFAULTBYTELENGTH > 0 ? 1 : 0) + 4;
			int num_char_in_row = PREFIX.length() + 1 + indexlength + 1 + bitmaplength + 1 + messagelength + 1 + 2;
			int allLength = 2 + num_row * num_char_in_row + 2;
			resultChars = new char[allLength];
			for (int i = 0; i < allLength; i++) {
				resultChars[i] = ' ';
			}
			/** 画出表格 */
			resultChars[0] = '\r';
			resultChars[1] = '\n';
			resultChars[allLength - 2] = '\r';
			resultChars[allLength - 1] = '\n';
			int PREFIX_length = PREFIX.length();
			int SUFFIX_length = SUFFIX.length();
			int P_1 = PREFIX_length; // 分割线1的位置
			int P_2 = PREFIX_length + 1 + indexlength; // 分割线2的位置
			int P_3 = PREFIX_length + 1 + indexlength + 1 + bitmaplength; // 分割线3的位置
			int P_4 = PREFIX_length + 1 + indexlength + 1 + bitmaplength + 1 + messagelength; // 分割线4的位置
			for (int i = 0; i < num_row; i++) {
				for (int j = 0; j < num_char_in_row - 2; j++) {
					int curr_char_index = 2 + (i * num_char_in_row) + j;
					if (j < PREFIX_length) {
						/** 设置打印区域左侧的空白对齐 */
						resultChars[curr_char_index] = ' ';
					} else {
						if (i == 0 || i == 2 || i == num_row - 1) {
							/** 设置打印区域的：顶、中、底 三行分隔符 */
							resultChars[curr_char_index] = '-';
						} else {
							if (j == P_1 || j == P_2 || j == P_3) {// || j == P_4去除尾分隔符
								/** 设置打印区域的：序号区域、位图区域、信息区域的四条纵向分隔符，由于信息区域的尾部位置未找到对齐方法，所以未把P_4分割画出来 */
								resultChars[curr_char_index] = '|';
							}
							if (i == 1 && j > P_2 && j < P_3) {
								/** 第二行:位图区域的标题部分 */
								if ((j - P_2) % 3 == 1) {
									resultChars[curr_char_index] = ' ';
								} else if ((j - P_2) % 3 == 2) {
									resultChars[curr_char_index] = '0';
								} else if ((j - P_2) % 3 == 0) {
									int count = (j - P_2) / 3 - 1;
									resultChars[curr_char_index] = (char) (count < 10 ? (count + '0')
											: (count - 10 + 'A'));
								}

							} else if (i >= 3 && j > P_1 && j < P_2) {
								/** 第三行开始的首部：序号区域 */
								int i_0, i_1, i_2, i_3, i_4, i_5, i_6, i_7;
								i_0 = ((i - 3) >> 28 & 0x0000000f);// 获取序号，取0~3位，是1byte
								i_1 = ((i - 3) >> 24 & 0x0000000f);// 获取序号，取4~7位，是1byte
								i_2 = ((i - 3) >> 20 & 0x0000000f);// 获取序号，取8~11位，是1byte
								i_3 = ((i - 3) >> 16 & 0x0000000f);// 获取序号，取12~15位，是1byte
								i_4 = ((i - 3) >> 12 & 0x0000000f);// 获取序号，取16~19位，是1byte
								i_5 = ((i - 3) >> 8 & 0x0000000f);// 获取序号，取20~23位，是1byte
								i_6 = ((i - 3) >> 4 & 0x0000000f);// 获取序号，取24~27位，是1byte
								i_7 = ((i - 3) & 0x0000000f);// 获取序号，取28~31位，是1byte
								int index = j - P_1 - 2;
								if (index == 0)
									resultChars[curr_char_index] = (char) (i_0 < 10 ? (i_0 + '0') : (i_0 - 10 + 'A'));
								if (index == 1)
									resultChars[curr_char_index] = (char) (i_1 < 10 ? (i_1 + '0') : (i_1 - 10 + 'A'));
								if (index == 2)
									resultChars[curr_char_index] = (char) (i_2 < 10 ? (i_2 + '0') : (i_2 - 10 + 'A'));
								if (index == 3)
									resultChars[curr_char_index] = (char) (i_3 < 10 ? (i_3 + '0') : (i_3 - 10 + 'A'));
								if (index == 4)
									resultChars[curr_char_index] = (char) (i_4 < 10 ? (i_4 + '0') : (i_4 - 10 + 'A'));
								if (index == 5)
									resultChars[curr_char_index] = (char) (i_5 < 10 ? (i_5 + '0') : (i_5 - 10 + 'A'));
								if (index == 6)
									resultChars[curr_char_index] = (char) (i_6 < 10 ? (i_6 + '0') : (i_6 - 10 + 'A'));
								if (index == 7)
									resultChars[curr_char_index] = (char) (i_7 < 10 ? (i_7 + '0') : (i_7 - 10 + 'A'));
							} else if (i >= 3 && j > P_2 && j < P_3) {
								/** 第三行开始的位图部 */
								// 避免最后一行时会发生的超出的现象
								// （当前j位置的char-分隔符 ）/3 = 当前字符是当前行的第几个字符
								// 当前字符是当前行的第几个字符+（i-3）*DEFAULTBYTELENGTH = 当前字符是当前行的第几个字符 + 已经打印过的字符 = 全体字符
								int bitmap_index = ((j - P_2 + 2) / 3 - 1) + (i - 3) * DEFAULTBYTELENGTH;
								// 如果全体字符大于message的长度，那么跳过该char
								if (bitmap_index > message.length - 1)
									continue;
								byte msg = message[bitmap_index];
								if ((j - P_2) % 3 == 1) {
									// resultChars[curr_char_index] = ' ';//可以不设置位图区域的空白字符，因为在本程序57行已经初始化为' '
								} else if ((j - P_2) % 3 == 2) {
									byte h4 = (byte) (msg >> 4 & 0x0000000f);
									resultChars[curr_char_index] = (char) (h4 < 10 ? h4 + '0' : (h4 - 10 + 'A'));
								} else if ((j - P_2) % 3 == 0) {
									byte l4 = (byte) (msg & 0x0000000f);
									resultChars[curr_char_index] = (char) (l4 < 10 ? l4 + '0' : (l4 - 10 + 'A'));
								}
							} else if (i >= 3 && j > P_3 && j < P_4) {
								/** 第三行开始的消息部 */
								if (j == (P_3 + SUFFIX_length)) {
									// 只有当 j == 分割线 + P_3后缀时，一次性打印当前的消息部
									byte[] tmpbytes = new byte[DEFAULTBYTELENGTH];
									int bitmap_index = (i - 3) * DEFAULTBYTELENGTH;
									// 有效的长度
									int useByte = ((message.length - bitmap_index >= DEFAULTBYTELENGTH) ? DEFAULTBYTELENGTH
											: message.length - bitmap_index);
									System.arraycopy(message, bitmap_index, tmpbytes, 0, useByte);
									for (int ii = 0; ii < tmpbytes.length; ii++) {
										byte b = tmpbytes[ii];
										if (b == '\r' || b == '\n' || b == '\t') {
											tmpbytes[ii] = '^';
										}
									}
									String tmp = new String(tmpbytes, 0, useByte);

									for (int ii = 0; ii < tmp.length(); ii++) {
										resultChars[curr_char_index + ii] = tmp.charAt(ii);
									}
								}
							}
						}
						/** 为每一行增加换行符 */
						if (j == num_char_in_row - 3) {
							resultChars[2 + (i * num_char_in_row) + j + 1] = '\r';
							resultChars[2 + (i * num_char_in_row) + j + 2] = '\n';
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultChars;
	}
}