package com.netease.lowcode.extension.video.spring.logic;

import com.netease.lowcode.core.annotation.NaslLogic;
import com.netease.lowcode.extension.video.spring.service.VideoService;
import com.netease.lowcode.extension.video.structure.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component("library-video_logic")
public class VideoLogic {

    private static VideoService videoService;

    @NaslLogic
    public static Response sliceVideo(String videoUrl,String filename) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        videoService.initDir();
        String key = videoService.sliceVideo(videoUrl,filename);
        String url = request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort() +
                "/rest/video/get/" + key;

        return Response.OK(url);
    }

    @Autowired
    @Qualifier("library-video_service")
    public void setVideoService(VideoService videoService) {
        VideoLogic.videoService = videoService;
    }
}
