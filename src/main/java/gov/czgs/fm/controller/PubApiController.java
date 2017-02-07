package gov.czgs.fm.controller;

import gov.czgs.fm.com.baidu.speech.serviceapi.HttpUtil;
import gov.czgs.fm.service.PubApiService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdky.restfull.configuration.Config;

@RestController
@RequestMapping(value = Config.URL_PUBLIC)
public class PubApiController {

	 @Autowired
	  private HttpServletRequest httpRequest;
	 @Autowired
	  private HttpServletResponse response;
	@Resource
	private PubApiService pubApiService;
	/**
	 * 获取栏目信息
	 * @return
	 */
	@RequestMapping(value = "/wzglmenu", method = RequestMethod.GET)
	public ResponseEntity<List<Map<String, Object>>> getAsideMenu() {
		List<Map<String, Object>> ls = pubApiService.getAsideMenu();
		return new ResponseEntity<>(ls, HttpStatus.OK);
	}
	/**
	 * 根据id获取文章信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/wzinfo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getWz(@PathVariable("id") String id) {
		Map<String, Object> obj = pubApiService.getWz(id);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	/**
	 * 根据栏目ID获取对应的文章信息
	 * @param lmid
	 * @return
	 */
	@RequestMapping(value = "/wzinfobylm/{lmid}", method = RequestMethod.GET)
	public ResponseEntity<?> getWzByLm(@PathVariable("lmid") String lmid) {
		List<Map<String, Object>> obj = pubApiService.getWzByLm(lmid);
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tts", method = RequestMethod.GET)
	public ResponseEntity<?> test() throws IOException {
		StringBuffer sql=new StringBuffer(" 相对于数据拷贝影响的明显，非常多的人会忽视了上下文切换对性能的影响。在我的经验里，比起数据拷贝，上下文切换是让高负载应用彻底完蛋的真正杀手。系统更多的时间都花费在线程切换上，而不是花在真正做有用工作的线程上。令人惊奇的是，（和数据拷贝相比）在同一个水平上，导致上下文切换原因总是更常见。引起环境切换的第一个原因往往是活跃线程数比CPU个数多。随着活跃线程数相对于CPU个数的增加，上下文切换的次数也在增加，如果你够幸运，这种增长是线性的，但更常见是指数增长。这个简单的事实解释了为什么每个连接对应一个单独线程的多线程设计模式的可伸缩性更差。对于一个可伸缩性的系统来说，限制活跃线程数少于或等于CPU个数是更有实际意义的方案。曾经这种方案的一个变种是只使用一个活跃线程，虽然这种方案避免了环境争用，同时也避免了锁，但它不能有效利用多CPU在增加总吞吐量上的价值，因此除非程序无CPU限制（non-CPU-bound），(通常是网络I/O限制 network-I/O-bound)，应该继续使用更实际的方案。 ");
		sql.append(" 　　一个有适量线程的程序首先要考虑的事情是规划出如何创建一个线程去管理多连接。这通常意味着前置一个select/poll,异步I/O，信号或者完成端口，而后台使用一个事件驱动的程序框架。关于哪种前置API是最好的有很多争论。 Dan Kegel的C10K问题在这个领域是一篇不错的论文。个人认为，select/poll和信号通常是一种丑陋的方案，因此我更倾向于使用AIO或者完成端口，但是实际上它并不会好太多。也许除了select()，它们都还不错。所以不要花太多精力去探索前置系统最外层内部到底发生了什么。 ");
		sql.append(" 　　对于最简单的多线程事件驱动服务器的概念模型,其内部有一个请求缓存队列，客户端请求被一个或者多个监听线程获取后放到队列里，然后一个或者多个工作线程从队列里面取出请求并处理。从概念上来说，这是一个很好的模型，有很多用这种方式来实现他们的代码。这会产生什么问题吗？引起环境切换的第二个原因是把对请求的处理从一个线程转移到另一个线程。有些人甚至把对请求的回应又切换回最初的线程去做，这真是雪上加霜，因为每一个请求至少引起了2次环境切换。把一个请求从监听线程转换到成工作线程，又转换回监听线程的过程中，使用一种“平滑”的方法来避免环境切换是非常重要的。此时，是否把连接请求分配到多个线程，或者让所有线程依次作为监听线程来服务每个连接请求，反而不重要了。 ");
		sql.append(" 　　即使在将来,也不可能有办法知道在服务器中同一时刻会有多少激活线程.毕竟，每时每刻都可能有请求从任意连接发送过来，一些进行特殊任务的“后台”线程也会在任意时刻被唤醒。那么如果你不知道当前有多少线程是激活的，又怎么能够限制激活线程的数量呢？根据我的经验，最简单同时也是最有效的方法之一是：用一个老式的带计数的信号量，每一个线程执行的时候就先持有信号量。如果信号量已经到了最大值，那些处于监听模式的线程被唤醒的时候可能会有一次额外的环境切换,(监听线程被唤醒是因为有连接请求到来, 此时监听线程持有信号量时发现信号量已满,所以即刻休眠), 接着它就会被阻塞在这个信号量上，一旦所有监听模式的线程都这样阻塞住了，那么它们就不会再竞争资源了，直到其中一个线程释放信号量，这样环境切换对系统的影响就可以忽略不计。更主要的是，这种方法使大部分时间处于休眠状态的线程避免在激活线程数中占用一个位置，这种方式比其它的替代方案更优雅。 ");
		sql.append(" 　　一旦处理请求的过程被分成两个阶段(监听和工作)，那么更进一步，这些处理过程在将来被分成更多的阶段(更多的线程)就是很自然的事了。最简单的情况是一个完整的请求先完成第一步,然后是第二步(比如回应)。然而实际会更复杂：一个阶段可能产生出两个不同执行路径，也可能只是简单的生成一个应答(例如返回一个缓存的值)。由此每个阶段都需要知道下一步该如何做，根据阶段分发函数的返回值有三种可能的做法： ");
		String sb = sql.toString();

		List<String> fdwz = getFdWz(sb);

		Vector<InputStream> v = new Vector<>();  
		for(String dw :fdwz){
			byte[] baos = HttpUtil.http(dw);
			ByteArrayInputStream bis = new ByteArrayInputStream(baos);
			v.addElement(bis);
		}

		Enumeration<InputStream> e = v.elements();  
		SequenceInputStream se = new SequenceInputStream(e);  
         response.setContentType("application/pdf");
			response.setHeader("pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setHeader("expires", "0");
			response.addHeader("Content-Disposition", "attachment;filename='t2a.mp3'");
			IOUtils.copyLarge(se, response.getOutputStream());
			response.flushBuffer();
		return new ResponseEntity<>("t2a.mp3", HttpStatus.OK);
	}
	
	private List<String> getFdWz(String fdwz){
		String[] list =fdwz.split("。");
		List<String> zw = new ArrayList<String>();
		int count = 0;
		String ls = "";
		for(int i = 0; i < list.length; i++)
		{
			//如果这一段超过1024字节 少于2048 字节  强行截半
			if(list[i].length()*4>1024){
				zw.add(ls+"。");
				String test =list[i];
				zw.add(test.substring(0, list[i].length()/2)+"。");
				zw.add(test.substring(list[i].length()/2,list[i].length())+"。");
				count = 0;
				ls ="";
			}else{
				count += list[i].length()*4;
				int len = ls.length()*4;
				if(count>1024){
					zw.add(ls+"。");
					count = list[i].length()*4;
					ls="";
				}
				ls += list[i];
			}
			if(i==list.length-1){
				zw.add(ls+"。");
			}
		}
		return zw;
	}
}
