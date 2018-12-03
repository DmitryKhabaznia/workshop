package workshop.service;

import workshop.db.entity.impl.User;
import workshop.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Service that intended to work with user avatars.
 */
public class AvatarService {

    private static final Logger LOG = Logger.getLogger(AvatarService.class);
    private static final String IMAGE_EXTENSION = ".jpeg";
    private static final String CANT_SET_AVATAR_ERROR_MESSAGE = "Avatar wasn't set";
    private String imageDirectoryPath;

    public AvatarService(String imageDirectoryPath) {
        this.imageDirectoryPath = imageDirectoryPath;
    }

    /**
     * Saves avatar to imageDirectoryPath.
     *
     * @param part {@link Part} that contains user avatar
     * @param user {@link User} which avatar is adding
     */
    public void setAvatar(Part part, User user) throws ServiceException {
        if (part.getSize() != 0) {
            try {
                part.write(imageDirectoryPath + File.separator + user.getLogin() + IMAGE_EXTENSION);
            } catch (IOException ex) {
                LOG.debug(CANT_SET_AVATAR_ERROR_MESSAGE, ex);
                throw new ServiceException(CANT_SET_AVATAR_ERROR_MESSAGE, ex);
            }
        }
    }

    /**
     * Returns {@link Path} of specified avatar.
     *
     * @param imageName specified image
     * @return {@link Path} of specified avatar
     */
    public Path getAvatarPath(HttpServletRequest request, String imageName) {
        LOG.debug("Getting avatar.");
        File avatar = new File(imageDirectoryPath + File.separator + imageName + IMAGE_EXTENSION);
        if (!avatar.exists()) {
            avatar = new File(request.getServletContext().getRealPath("/images/default_avatar" + IMAGE_EXTENSION));
        }
        return avatar.toPath();
    }

}
