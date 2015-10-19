Ext.define('Admin.view.forms.WizardForm', {
    extend: 'Ext.panel.Panel',
    xtype: 'wizardform',
    requires: ['Admin.view.forms.WizardFormModel'],

    bodyPadding: 15,

    height: 340,

    layout: 'card',

    viewModel: {
        type: 'wizardform'
    },

    controller: 'wizardform',

    defaults: {
        /*
         * Seek out the first enabled, focusable, empty textfield when the form is focused
         */
        defaultFocus: 'textfield:not([value]):focusable:not([disabled])',

        defaultButton: 'nextbutton'
    },

    items: [
        {
            xtype: 'form',
            defaultType: 'textfield',
            defaults: {
                labelWidth: 90,
                labelAlign: 'top',
                labelSeparator: '',
                submitEmptyText: false,
                anchor: '100%'
            },
            items: [
                {
                    xtype: 'tagfield',
                    name: 'certType',
                    store: {
                        fields: ['value', 'name'],
                        data: [
                            {"value": "0", "name": "000"},
                            {"value": "1", "name": "111"},
                            {"value": "2", "name": "222"},
                            {"value": "3", "name": "333"},
                            {"value": "4", "name": "444"},
                            {"value": "5", "name": "555"},
                            {"value": "6", "name": "666"},
                            {"value": "7", "name": "777"},
                            {"value": "8", "name": "888"},
                            {"value": "9", "name": "999"},
                            {"value": "A", "name": "AAA"},
                            {"value": "B", "name": "BBB"},
                            {"value": "C", "name": "CCC"},
                            {"value": "X", "name": "DDD"}
                        ]
                    },
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'value',
                    createNewOnEnter: true,
                    createNewOnBlur: true,
                    filterPickList: true,
                    value: '0'
                },
                {
                    xtype: 'datefield',
                    name: 'date1'
                },
                //{
                //    xtype: 'numberfield',
                //    name: 'basic',
                //    value: 1,
                //    minValue: 1,
                //    maxValue: 125
                //},
                //{
                //    xtype: 'slider',
                //    fieldLabel: 'Ambient Sounds',
                //    value: 80,
                //    name: 'ambient'
                //},
                {
                    xtype: 'filefield',
                    name: 'file1'
                },
                {
                    xtype: 'combobox',
                    name: 'combobox',
                    store: {
                        fields: ['value', 'name'],
                        data: [
                            {"value": "0", "name": "0"},
                            {"value": "1", "name": "1"},
                            {"value": "2", "name": "2"},
                            {"value": "3", "name": "3"},
                            {"value": "4", "name": "4"},
                            {"value": "5", "name": "5"},
                            {"value": "6", "name": "6"},
                            {"value": "7", "name": "7"},
                            {"value": "8", "name": "8"},
                            {"value": "9", "name": "9"},
                            {"value": "A", "name": "A"},
                            {"value": "B", "name": "B"},
                            {"value": "C", "name": "C"},
                            {"value": "X", "name": "D"}
                        ]
                    },
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'value',
                    value: '0'
                },
            ]
        },
        {
            xtype: 'form',
            defaultType: 'textfield',
            defaults: {
                labelWidth: 90,
                labelAlign: 'top',
                labelSeparator: '',
                submitEmptyText: false,
                anchor: '100%'
            },
            items: [
                {
                    emptyText: 'First Name'
                },
                {
                    emptyText: 'Last Name'
                },
                {
                    emptyText: 'Company'
                },
                {
                    xtype: 'fieldcontainer',
                    cls: 'wizard-form-break',
                    fieldLabel: 'MemberType',
                    defaultType: 'radiofield',
                    defaults: {
                        flex: 1
                    },
                    layout: 'hbox',
                    items: [
                        {
                            boxLabel: 'Free',
                            name: 'MemberType',
                            inputValue: 'Free'
                        }, {
                            boxLabel: 'Personal',
                            name: 'MemberType',
                            inputValue: 'Perosnal'
                        }, {
                            boxLabel: 'Black',
                            name: 'MemberType',
                            inputValue: 'Business'
                        }
                    ]
                }
            ]
        },
        {
            xtype: 'form',
            defaultType: 'textfield',
            defaults: {
                labelWidth: 90,
                labelAlign: 'top',
                labelSeparator: '',
                submitEmptyText: false,
                anchor: '100%'
            },
            items: [
                {
                    emptyText: 'Phone number'
                },
                {
                    emptyText: 'Address'
                },
                {
                    emptyText: 'City'
                },
                {
                    emptyText: 'Postal Code / Zip Code'
                }
            ]
        },
        {
            xtype: 'form',
            items: [
                {
                    html: '<h2>Thank You</h2><p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>'
                }
            ]
        }
    ],

    initComponent: function () {

        this.tbar = {
            reference: 'progress',
            defaultButtonUI: 'wizard-' + this.colorScheme,
            cls: 'wizardprogressbar',
            defaults: {
                disabled: true,
                iconAlign: 'top'
            },
            layout: {
                pack: 'center'
            },
            items: [
                {
                    step: 0,
                    iconCls: 'fa fa-info',
                    pressed: true,
                    enableToggle: true,
                    text: 'Account'
                },
                {
                    step: 1,
                    iconCls: 'fa fa-user',
                    enableToggle: true,
                    text: 'Profile'
                },
                {
                    step: 2,
                    iconCls: 'fa fa-home',
                    enableToggle: true,
                    text: 'Address'
                },
                {
                    step: 3,
                    iconCls: 'fa fa-heart',
                    enableToggle: true,
                    text: 'Finish'
                }
            ]
        };

        this.bbar = {
            reference: 'navigation-toolbar',
            margin: 8,
            items: [
                '->',
                {
                    text: 'Previous',
                    ui: this.colorScheme,
                    formBind: true,
                    bind: {
                        disabled: '{atBeginning}'
                    },
                    listeners: {
                        click: 'onPreviousClick'
                    }
                },
                {
                    text: 'Next',
                    ui: this.colorScheme,
                    formBind: true,
                    reference: 'nextbutton',
                    bind: {
                        disabled: '{atEnd}'
                    },
                    listeners: {
                        click: 'onNextClick'
                    }
                }
            ]
        };

        this.callParent();
    }
});
