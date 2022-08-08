create unique index IX_BDDBF1D7 on FVSActiveEntry (userId, clayDataSetDisplayId[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:200$]);
create index IX_D023E543 on FVSActiveEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_B3581738 on FVSCustomEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_2943B369 on FVSEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_39A73719 on FVSFrontendDataSet (fdsName[$COLUMN_LENGTH:75$], system_);
create index IX_95F2C053 on FVSFrontendDataSet (userId, fdsName[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:75$], system_);
create index IX_1AA54E9D on FVSFrontendDataSet (uuid_[$COLUMN_LENGTH:75$], companyId);