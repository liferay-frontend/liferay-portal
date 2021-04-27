create table DatasetCustomViewEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	datasetCustomViewEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	datasetDisplayId VARCHAR(75) null,
	name VARCHAR(75) null,
	plid LONG,
	portletId VARCHAR(75) null,
	settingsJSON TEXT null
);