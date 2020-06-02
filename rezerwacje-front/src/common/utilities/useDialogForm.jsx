//custom react hook for forms in modal dialogs
import { useState, useEffect } from 'react';

const useDialogForm = (initState, callback, validate) => {
  const [values, setValues] = useState(initState);
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (name, value) => {
    setValues({ ...values, [name]: value });
  };

  const handleChangeEvent = (event) => {
    const { name, value } = event.target;
    handleChange(name, value);
  };

  useEffect(() => {
    setValues({ ...initState });
  }, [initState]);

  const handleSubmit = async () => {
    setIsSubmitting(true);
    setErrors(await validate(values));
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      callback();
    }
  }, [errors]);

  return {
    handleChange,
    handleChangeEvent,
    handleSubmit,
    values,
    errors,
  };
};

export default useDialogForm;
