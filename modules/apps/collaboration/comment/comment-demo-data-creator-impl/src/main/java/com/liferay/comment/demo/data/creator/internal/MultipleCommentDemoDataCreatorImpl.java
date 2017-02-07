/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.comment.demo.data.creator.internal;

import com.liferay.comment.demo.data.creator.CommentDemoDataCreator;
import com.liferay.comment.demo.data.creator.MultipleCommentDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = MultipleCommentDemoDataCreator.class)
public class MultipleCommentDemoDataCreatorImpl
	implements MultipleCommentDemoDataCreator {

	@Override
	public void create(List<Long> userIds, ClassedModel classedModel)
		throws PortalException {

		int commentsCount = RandomUtil.nextInt(_MAX_COMMENTS);

		_addComments(userIds, classedModel, commentsCount);
	}

	@Override
	public void delete() throws PortalException {
		_commentDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setCommentDemoDataCreator(
		CommentDemoDataCreator commentDemoDataCreator) {

		_commentDemoDataCreator = commentDemoDataCreator;
	}

	private void _addComments(
			List<Long> userIds, ClassedModel classedModel, int commentsCount)
		throws PortalException {

		int commentsCreated = 0;

		while (commentsCount > commentsCreated) {
			long userId = _getRandomElement(userIds);

			_commentDemoDataCreator.create(userId, classedModel);

			commentsCreated++;
		}
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final int _MAX_COMMENTS = 100;

	private CommentDemoDataCreator _commentDemoDataCreator;

}