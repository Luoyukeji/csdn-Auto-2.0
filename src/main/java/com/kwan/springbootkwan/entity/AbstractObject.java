package com.kwan.springbootkwan.entity;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractObject {
    public AbstractObject() {
    }

    public <T> T clone(Class<T> targetClazz) {
        try {
            T target = targetClazz.newInstance();
            BeanCopierUtils.copyProperties(this, target);
            return this.getTarget(target);
        } catch (Exception var3) {
            throw new RuntimeException("error", var3);
        }
    }

    public <T> T clone(T target) {
        try {
            BeanCopierUtils.copyProperties(this, target);
            return this.getTarget(target);
        } catch (Exception var3) {
            throw new RuntimeException("error", var3);
        }
    }

    public <T> T clone(Class<T> targetClazz, Integer cloneDirection) {
        try {
            T target = targetClazz.newInstance();
            BeanCopierUtils.copyProperties(this, target);
            Class<?> thisClazz = this.getClass();
            List<Field> thisFields = this.listThisField((List) null, thisClazz);
            Iterator var6 = thisFields.iterator();

            while (true) {
                Field thisField;
                Class cloneTargetClazz;
                String name;
                AbstractObject sourceObj;
                do {
                    while (true) {
                        Class sourceFieldClazz;
                        do {
                            do {
                                do {
                                    do {
                                        do {
                                            do {
                                                do {
                                                    do {
                                                        do {
                                                            do {
                                                                do {
                                                                    do {
                                                                        do {
                                                                            do {
                                                                                do {
                                                                                    do {
                                                                                        do {
                                                                                            do {
                                                                                                do {
                                                                                                    label79:
                                                                                                    do {
                                                                                                        while (var6.hasNext()) {
                                                                                                            thisField = (Field) var6.next();
                                                                                                            thisField.setAccessible(true);
                                                                                                            if (!Collection.class.isAssignableFrom(thisField.getType())) {
                                                                                                                sourceFieldClazz = thisField.getType();
                                                                                                                continue label79;
                                                                                                            }

                                                                                                            Collection<?> list = (Collection) thisField.get(this);
                                                                                                            if (list != null && list.size() != 0) {
                                                                                                                Field targetField = null;

                                                                                                                try {
                                                                                                                    targetField = this.getTargetClazzField(thisField, targetClazz);
                                                                                                                } catch (NoSuchFieldException var16) {
                                                                                                                    continue;
                                                                                                                }

                                                                                                                if (targetField != null) {
                                                                                                                    cloneTargetClazz = this.getTargetListGenericType(targetField);
                                                                                                                    Collection clonedList = (Collection) thisField.get(this).getClass().newInstance();
                                                                                                                    this.cloneList(list, clonedList, cloneTargetClazz, cloneDirection);
                                                                                                                    name = thisField.getName();
                                                                                                                    name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                                                                                                                    Method setFieldMethod = targetClazz.getMethod(name, targetField.getType());
                                                                                                                    setFieldMethod.invoke(target, clonedList);
                                                                                                                }
                                                                                                            }
                                                                                                        }

                                                                                                        return target;
                                                                                                    } while (sourceFieldClazz == String.class);
                                                                                                } while (sourceFieldClazz == Long.class);
                                                                                            } while ("long".equals(sourceFieldClazz.toString()));
                                                                                        } while (thisField.getType() == Integer.class);
                                                                                    } while ("int".equals(sourceFieldClazz.toString()));
                                                                                } while (sourceFieldClazz == Short.class);
                                                                            } while ("short".equals(sourceFieldClazz.toString()));
                                                                        } while (sourceFieldClazz == Double.class);
                                                                    } while ("double".equals(sourceFieldClazz.toString()));
                                                                } while (sourceFieldClazz == Float.class);
                                                            } while ("float".equals(sourceFieldClazz.toString()));
                                                        } while (sourceFieldClazz == BigDecimal.class);
                                                    } while (sourceFieldClazz == Boolean.class);
                                                } while ("boolean".equals(sourceFieldClazz.toString()));
                                            } while (sourceFieldClazz == Date.class);
                                        } while (sourceFieldClazz == Character.class);
                                    } while ("char".equals(sourceFieldClazz.toString()));
                                } while (sourceFieldClazz == Byte.class);
                            } while ("byte".equals(sourceFieldClazz.toString()));
                        } while (sourceFieldClazz == java.sql.Date.class);

                        try {
                            if (!(thisField.getType().newInstance() instanceof AbstractObject)) {
                                continue;
                            }
                            break;
                        } catch (Exception var17) {
                            if (!(var17 instanceof InstantiationException)) {
                                throw new RuntimeException("error", var17);
                            }
                        }
                    }

                    sourceObj = (AbstractObject) ((AbstractObject) thisField.get(this));
                } while (sourceObj == null);

                cloneTargetClazz = null;

                Field targetField;
                try {
                    targetField = this.getTargetClazzField(thisField, targetClazz);
                } catch (NoSuchFieldException var18) {
                    continue;
                }

                if (targetField != null) {
                    cloneTargetClazz = targetField.getType();
                    AbstractObject clonedObj = (AbstractObject) sourceObj.clone(cloneTargetClazz, cloneDirection);
                    name = thisField.getName();
                    String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method setFieldMethod = targetClazz.getMethod(setMethodName, targetField.getType());
                    setFieldMethod.invoke(target, clonedObj);
                }
            }
        } catch (Exception var19) {
            throw new RuntimeException("error", var19);
        }
    }

    public List<Field> listThisField(List<Field> thisFields, Class<?> thisClazz) {
        if (thisFields == null) {
            thisFields = new ArrayList(Arrays.asList(thisClazz.getDeclaredFields()));
        } else {
            ((List) thisFields).addAll(Arrays.asList(thisClazz.getDeclaredFields()));
        }

        if (!thisClazz.getSuperclass().getTypeName().equals(AbstractObject.class.getTypeName())) {
            this.listThisField((List) thisFields, thisClazz.getSuperclass());
        }

        return (List) thisFields;
    }

    private Field getTargetClazzField(Field thisField, Class<?> targetClazz) throws NoSuchFieldException {
        Field targetField = null;

        try {
            targetField = targetClazz.getDeclaredField(thisField.getName());
        } catch (NoSuchFieldException var6) {
            if (targetClazz.getSuperclass() != null) {
                String targetSuperClazzTypeName = targetClazz.getSuperclass().getTypeName();
                if (!targetSuperClazzTypeName.equals(Object.class.getTypeName()) && !targetSuperClazzTypeName.equals(AbstractObject.class.getTypeName())) {
                    targetField = this.getTargetClazzField(thisField, targetClazz.getSuperclass());
                }
            }

            if (targetField == null) {
                throw var6;
            }
        }

        return targetField;
    }

    private void cloneList(Collection sourceList, Collection targetList, Class cloneTargetClazz, Integer cloneDirection) {
        Iterator var5 = sourceList.iterator();

        while (var5.hasNext()) {
            Object object = var5.next();
            if (object instanceof AbstractObject) {
                AbstractObject targetObject = (AbstractObject) object;
                AbstractObject clonedObject = (AbstractObject) targetObject.clone(cloneTargetClazz, cloneDirection);
                targetList.add(clonedObject);
            } else {
                targetList.add(object);
            }
        }

    }

    private Class<?> getTargetListGenericType(Field targetField) {
        Type genericType = targetField.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            return (Class) parameterizedType.getActualTypeArguments()[0];
        } else {
            return null;
        }
    }

    private <T> T getTarget(T target) throws Exception {
        Class<?> targetClazz = target.getClass();
        Field[] fields = targetClazz.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            field.setAccessible(true);
            if (field.getType() == List.class) {
                List<?> list = (List) field.get(target);
                if (list != null && list.size() != 0) {
                    Class<?> targetListGenericTypeClazz = this.getTargetListGenericType(field);
                    if (targetListGenericTypeClazz == null || this.isAbstractObjectClass(targetListGenericTypeClazz)) {
                        String name = field.getName();
                        String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                        Method setFieldMethod = targetClazz.getMethod(setMethodName, field.getType());
                        setFieldMethod.invoke(target, new ArrayList());
                    }
                }
            }
        }

        return target;
    }

    private boolean isAbstractObjectClass(Class clazz) {
        if (clazz.getSuperclass() != null) {
            String superClazzTypeName = clazz.getSuperclass().getTypeName();
            if (superClazzTypeName.equals(Object.class.getTypeName())) {
                return false;
            } else {
                return superClazzTypeName.equals(AbstractObject.class.getTypeName()) ? true : this.isAbstractObjectClass(clazz.getSuperclass());
            }
        } else {
            return false;
        }
    }
}
